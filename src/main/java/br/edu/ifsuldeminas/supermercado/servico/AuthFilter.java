package br.edu.ifsuldeminas.supermercado.servico;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

/**
 * Intercepta TODAS as requisições (/*) antes de chegarem a qualquer Servlet ou JSP.
 * Se a página for pública, deixa passar. Se for protegida, só deixa passar quando
 * existir um usuário autenticado na sessão; caso contrário, redireciona ao login.
 */
@WebFilter(urlPatterns = "/*")
public class AuthFilter implements Filter {

    /** Nome do atributo de sessão que guarda o Usuario autenticado. */
    public static final String ATRIBUTO_SESSAO = "usuarioLogado";

    /** Páginas e servlets acessíveis sem login. Todo o resto é protegido. */
    private static final Set<String> PUBLICOS = Set.of(
            "/login.jsp",
            "/CadastrarUsuario.jsp",
            "/EsqueciSenha.jsp",
            "/RedefinirSenha.jsp",
            WebConstante.BASE_PATH + "/LoginControlador",
            WebConstante.BASE_PATH + "/UsuarioControlador",
            WebConstante.BASE_PATH + "/RecuperarSenhaControlador");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (ehPublico(caminho(req))) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession sessao = req.getSession(false); // false: NÃO cria sessão nova
        boolean autenticado = sessao != null && sessao.getAttribute(ATRIBUTO_SESSAO) != null;

        if (autenticado) {
            // Impede que o navegador guarde a página em cache: depois do logout,
            // o botão "voltar" não deve mostrar conteúdo protegido.
            resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            resp.setHeader("Pragma", "no-cache");
            resp.setDateHeader("Expires", 0);
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?aviso=login");
        }
    }

    /** Caminho já decodificado e normalizado pelo contêiner, sem o contexto da aplicação. */
    private static String caminho(HttpServletRequest req) {
        String servletPath = req.getServletPath() == null ? "" : req.getServletPath();
        String pathInfo = req.getPathInfo() == null ? "" : req.getPathInfo();
        return servletPath + pathInfo;
    }

    private static boolean ehPublico(String caminho) {
        return PUBLICOS.contains(caminho) || caminho.startsWith("/css/");
    }
}
