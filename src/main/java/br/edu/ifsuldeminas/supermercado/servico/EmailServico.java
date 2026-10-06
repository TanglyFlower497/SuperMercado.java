package br.edu.ifsuldeminas.supermercado.servico;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.util.Properties;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Envio do e-mail de recuperação de senha via SMTP (Jakarta Mail).
 *
 * Configuração por variáveis de ambiente (ou -D na JVM):
 *   MAIL_USER  conta SMTP (ex.: seuemail@gmail.com)
 *   MAIL_PASS  senha da conta; no Gmail use uma "Senha de app" (exige verificação em 2 etapas)
 *   MAIL_HOST  padrão smtp.gmail.com
 *   MAIL_PORT  padrão 587 (STARTTLS); 465 usa SSL direto
 *   MAIL_FROM  remetente exibido; padrão = MAIL_USER
 */
public class EmailServico {

    private static final Logger LOG = Logger.getLogger(EmailServico.class.getName());

    private EmailServico() {
    }

    public static boolean enviarLinkRecuperacao(String destino, String nomeUsuario, String link, int validadeMinutos) {
        final String usuarioSmtp = AppConfig.get("MAIL_USER", null);
        final String senhaSmtp = AppConfig.get("MAIL_PASS", null);

        if (usuarioSmtp == null || senhaSmtp == null) {
            // Modo desenvolvimento: sem SMTP configurado, mostra o link no log do servidor.
            LOG.warning("[DEV] MAIL_USER/MAIL_PASS não configurados: e-mail NÃO enviado. "
                    + "Link de recuperação para " + destino + ": " + link);
            return false;
        }

        String host = AppConfig.get("MAIL_HOST", "smtp.gmail.com");
        int porta = Integer.parseInt(AppConfig.get("MAIL_PORT", "587"));
        String remetente = AppConfig.get("MAIL_FROM", usuarioSmtp);

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", String.valueOf(porta));
        props.put("mail.smtp.auth", "true");
        if (porta == 465) {
            props.put("mail.smtp.ssl.enable", "true");
        } else {
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.starttls.required", "true");
        }
        // Sempre confere se o certificado pertence mesmo ao servidor SMTP (anti "man-in-the-middle")
        props.put("mail.smtp.ssl.checkserveridentity", "true");
        configurarConfiancaTls(props, host);
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");
        props.put("mail.smtp.writetimeout", "10000");

        Session sessao = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuarioSmtp, senhaSmtp);
            }
        });

        String corpo = "Olá, " + nomeUsuario + "!\n\n"
                + "Recebemos um pedido para redefinir a senha da sua conta no sistema Atacado.\n"
                + "Para escolher uma nova senha, acesse o link abaixo (válido por " + validadeMinutos
                + " minutos e utilizável uma única vez):\n\n"
                + link + "\n\n"
                + "Se você não fez esse pedido, ignore este e-mail: sua senha continua a mesma.\n";

        try {
            MimeMessage msg = new MimeMessage(sessao);
            msg.setFrom(new InternetAddress(remetente, "Atacado"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino, false));
            msg.setSubject("Recuperação de senha - Atacado", "UTF-8");
            msg.setText(corpo, "UTF-8");
            Transport.send(msg);
            return true;
        } catch (MessagingException | UnsupportedEncodingException e) {
            // registra a exceção completa (inclui a causa: senha recusada, timeout, TLS, etc.)
            LOG.log(Level.SEVERE, "Falha ao enviar e-mail de recuperação via " + host + ":" + porta + " como "
                    + usuarioSmtp, e);
            return false;
        }
    }

    /**
     * O GlassFish aponta a JVM para o próprio "cacerts.jks" (-Djavax.net.ssl.trustStore), que é antigo e pode
     * não conter a autoridade certificadora do Google, causando "PKIX path building failed".
     * Aqui o envio de e-mail passa a confiar nas CAs do cacerts do JDK (muito mais atualizado),
     * SEM mexer no restante do servidor.
     *
     * Último recurso (inseguro, desligado por padrão): MAIL_TRUST_HOST=true faz o JavaMail aceitar
     * qualquer certificado apresentado por este host. Use só se a rede interceptar o TLS e você não
     * puder importar a CA dela; na prática, só para demonstração.
     */
    private static void configurarConfiancaTls(Properties props, String host) {
        if ("true".equalsIgnoreCase(AppConfig.get("MAIL_TRUST_HOST", "false"))) {
            LOG.warning("MAIL_TRUST_HOST=true: o certificado de " + host + " NÃO será validado (inseguro).");
            props.put("mail.smtp.ssl.trust", host);
            return;
        }
        try {
            Path cacerts = Paths.get(System.getProperty("java.home"), "lib", "security", "cacerts");
            if (!Files.isReadable(cacerts)) {
                LOG.warning("cacerts do JDK não encontrado em " + cacerts + "; usando o truststore padrão da JVM.");
                return;
            }
            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            try (InputStream in = Files.newInputStream(cacerts)) {
                ks.load(in, "changeit".toCharArray()); // senha padrão do cacerts do JDK
            }
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(ks);
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, tmf.getTrustManagers(), null);
            SSLSocketFactory fabrica = ctx.getSocketFactory();
            props.put("mail.smtp.ssl.socketFactory", fabrica);
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Não foi possível carregar o cacerts do JDK; usando o truststore padrão da JVM.", e);
        }
    }
}