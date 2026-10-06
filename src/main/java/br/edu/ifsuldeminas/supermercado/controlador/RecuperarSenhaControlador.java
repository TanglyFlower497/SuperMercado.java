package br.edu.ifsuldeminas.supermercado.controlador;

import br.edu.ifsuldeminas.supermercado.entidade.Usuario;
import br.edu.ifsuldeminas.supermercado.modelo.dao.TokenRecuperacaoDao;
import br.edu.ifsuldeminas.supermercado.modelo.dao.UsuarioDao;
import br.edu.ifsuldeminas.supermercado.servico.AppConfig;
import br.edu.ifsuldeminas.supermercado.servico.EmailServico;
import br.edu.ifsuldeminas.supermercado.servico.SegurancaUtil;
import br.edu.ifsuldeminas.supermercado.servico.WebConstante;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Fluxo "Esqueci minha senha":
 *   POST opcao=solicitar -> recebe o e-mail, gera o token e envia o link
 *   GET  opcao=redefinir -> o usuário abre o link do e-mail; se o token vale, mostra o formulário
 *   POST opcao=salvar    -> grava a nova senha (BCrypt) e invalida o token
 */
@WebServlet(WebConstante.BASE_PATH + "/RecuperarSenhaControlador")
public class RecuperarSenhaControlador extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(RecuperarSenhaControlador.class.getName());
    private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    private static final String LINK_INVALIDO =
            "Link inválido, expirado ou já utilizado. Solicite um novo link de recuperação.";

    private final UsuarioDao usuarioDao = new UsuarioDao();
    private final TokenRecuperacaoDao tokenDao = new TokenRecuperacaoDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!"redefinir".equals(request.getParameter("opcao"))) {
            response.sendRedirect(request.getContextPath() + "/EsqueciSenha.jsp");
            return;
        }

        // O token está na URL: evita que vá no cabeçalho Referer para outros sites
        response.setHeader("Referrer-Policy", "no-referrer");

        String token = valor(request.getParameter("token"));
        if (token.isEmpty() || !tokenDao.tokenValido(SegurancaUtil.hashToken(token))) {
            request.setAttribute("erro", LINK_INVALIDO);
            request.getRequestDispatcher("/EsqueciSenha.jsp").forward(request, response);
            return;
        }

        request.setAttribute("token", token);
        request.getRequestDispatcher("/RedefinirSenha.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String opcao = valor(request.getParameter("opcao"));
        if ("solicitar".equals(opcao)) {
            solicitar(request, response);
        } else if ("salvar".equals(opcao)) {
            salvar(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/EsqueciSenha.jsp");
        }
    }

    private void solicitar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = valor(request.getParameter("email"));

        if (email.length() > 255 || !EMAIL.matcher(email).matches()) {
            request.setAttribute("erro", "Informe um e-mail válido.");
            request.setAttribute("email", email);
            request.getRequestDispatcher("/EsqueciSenha.jsp").forward(request, response);
            return;
        }

        // 1. Existe usuário com esse e-mail?
        Usuario usuario = usuarioDao.buscarPorEmail(email);
        if (usuario == null) {
            LOG.info("[RECUPERACAO] Nenhum usuário cadastrado com o e-mail '" + email
                    + "'. Nenhum e-mail enviado (a tela mostra mensagem genérica de propósito).");
        } else {
            // 2. Gera o token (aleatório, 256 bits). Só o SHA-256 dele vai ao banco.
            String token = SegurancaUtil.gerarToken();
            boolean salvo = tokenDao.criar(usuario.getId(), SegurancaUtil.hashToken(token),
                    SegurancaUtil.VALIDADE_TOKEN_MINUTOS);
            if (!salvo) {
                LOG.severe("[RECUPERACAO] Falha ao gravar o token no banco. A tabela token_recuperacao existe? "
                        + "Veja o erro SQL acima neste log.");
            } else {
                // 3. Envia o e-mail com o link que carrega o token
                String link = urlBase(request) + WebConstante.BASE_PATH
                        + "/RecuperarSenhaControlador?opcao=redefinir&token=" + token;
                boolean enviado = EmailServico.enviarLinkRecuperacao(usuario.getEmail(), usuario.getNomeUsuario(),
                        link, SegurancaUtil.VALIDADE_TOKEN_MINUTOS);
                LOG.info("[RECUPERACAO] Envio de e-mail para " + usuario.getEmail()
                        + (enviado ? ": OK (aceito pelo servidor SMTP)." : ": FALHOU (veja a mensagem acima)."));
            }
        }

        // Mesma resposta exista o e-mail ou não: não revela quem tem cadastro
        request.setAttribute("mensagem", "Se o e-mail informado estiver cadastrado, enviamos um link para "
                + "redefinir a senha (válido por " + SegurancaUtil.VALIDADE_TOKEN_MINUTOS
                + " minutos). Confira também a caixa de spam.");
        request.getRequestDispatcher("/EsqueciSenha.jsp").forward(request, response);
    }

    private void salvar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = valor(request.getParameter("token"));
        String senha = request.getParameter("senha");
        String confirmaSenha = request.getParameter("confirmaSenha");
        String tokenHash = SegurancaUtil.hashToken(token);

        if (token.isEmpty() || !tokenDao.tokenValido(tokenHash)) {
            request.setAttribute("erro", LINK_INVALIDO);
            request.getRequestDispatcher("/EsqueciSenha.jsp").forward(request, response);
            return;
        }

        String erro = SegurancaUtil.validarSenha(senha, confirmaSenha);
        if (erro != null) {
            request.setAttribute("erro", erro);
            request.setAttribute("token", token);
            request.getRequestDispatcher("/RedefinirSenha.jsp").forward(request, response);
            return;
        }

        // 6. novo hash BCrypt  7. atualiza a senha  8. invalida o token (tudo numa transação)
        String novoHash = SegurancaUtil.gerarHashSenha(senha);
        if (!tokenDao.redefinirSenha(tokenHash, novoHash)) {
            request.setAttribute("erro", LINK_INVALIDO);
            request.getRequestDispatcher("/EsqueciSenha.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/login.jsp?aviso=senha-alterada");
    }

    /**
     * URL pública da aplicação usada no link do e-mail. Prefira configurar APP_BASE_URL
     * (ex.: http://localhost:8080/Supermercado-1.0-SNAPSHOT); o cabeçalho Host da requisição
     * pode ser forjado por um atacante, por isso é só o plano B.
     */
    private String urlBase(HttpServletRequest request) {
        String configurada = AppConfig.get("APP_BASE_URL", null);
        if (configurada != null) {
            return configurada.replaceAll("/+$", "");
        }
        String esquema = request.getScheme();
        int porta = request.getServerPort();
        StringBuilder sb = new StringBuilder(esquema).append("://").append(request.getServerName());
        boolean padrao = ("http".equals(esquema) && porta == 80) || ("https".equals(esquema) && porta == 443);
        if (!padrao) {
            sb.append(':').append(porta);
        }
        return sb.append(request.getContextPath()).toString();
    }

    private static String valor(String s) {
        return s == null ? "" : s.trim();
    }
}
