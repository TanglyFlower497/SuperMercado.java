package br.edu.ifsuldeminas.supermercado.servico;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Funções de segurança: hash de senha (BCrypt), geração e hash de tokens.
 */
public final class SegurancaUtil {

    public static final int SENHA_MIN = 6;
    /** BCrypt só considera os primeiros 72 bytes da senha; acima disso seria truncada em silêncio. */
    public static final int SENHA_MAX = 72;
    public static final int VALIDADE_TOKEN_MINUTOS = 30;

    /** "Custo" do BCrypt: cada +1 dobra o tempo de cálculo (e de um ataque de força bruta). */
    private static final int BCRYPT_CUSTO = 12;
    private static final SecureRandom RANDOM = new SecureRandom();

    /** Hash de uma senha qualquer, usado só para gastar o mesmo tempo quando o usuário não existe. */
    private static final String HASH_FALSO = BCrypt.hashpw("senha-falsa", BCrypt.gensalt(BCRYPT_CUSTO));

    private SegurancaUtil() {
    }

    /** Gera o hash BCrypt (com salt aleatório embutido). Ex.: $2a$12$.... (60 caracteres) */
    public static String gerarHashSenha(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt(BCRYPT_CUSTO));
    }

    /**
     * Confere a senha digitada com o hash salvo. Se o hash for null (usuário inexistente),
     * ainda executa um checkpw "de mentira" para que o tempo de resposta seja parecido
     * e não dê para descobrir quais usuários existem medindo o tempo.
     */
    public static boolean verificarSenha(String senha, String hash) {
        if (senha == null) {
            senha = "";
        }
        if (hash == null) {
            BCrypt.checkpw(senha, HASH_FALSO);
            return false;
        }
        try {
            return BCrypt.checkpw(senha, hash);
        } catch (IllegalArgumentException e) {
            return false; // hash malformado no banco
        }
    }

    /** Valida a nova senha. Retorna a mensagem de erro, ou null se estiver tudo certo. */
    public static String validarSenha(String senha, String confirmacao) {
        if (senha == null || senha.length() < SENHA_MIN || senha.length() > SENHA_MAX) {
            return "A senha deve ter entre " + SENHA_MIN + " e " + SENHA_MAX + " caracteres.";
        }
        if (!senha.equals(confirmacao)) {
            return "A confirmação de senha não confere.";
        }
        return null;
    }

    /** Token aleatório de 256 bits (32 bytes) em Base64 seguro para URL (43 caracteres). */
    public static String gerarToken() {
        byte[] bytes = new byte[32];
        RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    /**
     * SHA-256 do token em hexadecimal (64 caracteres). No banco guardamos só este hash:
     * se alguém ler a tabela, não consegue montar um link de recuperação válido.
     */
    public static String hashToken(String token) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(token.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(64);
            for (byte b : digest) {
                sb.append(Character.forDigit((b >> 4) & 0xF, 16)).append(Character.forDigit(b & 0xF, 16));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 indisponível", e);
        }
    }
}
