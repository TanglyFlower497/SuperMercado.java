package br.edu.ifsuldeminas.supermercado.controlador;

import br.edu.ifsuldeminas.supermercado.entidade.Usuario;
import br.edu.ifsuldeminas.supermercado.modelo.dao.UsuarioDao;
import br.edu.ifsuldeminas.supermercado.servico.AuthFilter;
import br.edu.ifsuldeminas.supermercado.servico.SegurancaUtil;
import br.edu.ifsuldeminas.supermercado.servico.WebConstante;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * POST -> login (valida usuário e senha, cria a sessão).
 * GET  -> logout (encerra a sessão). É o link "Logout" do menu.
 */
@WebServlet(WebConstante.BASE_PATH + "/LoginControlador")
public class LoginControlador extends HttpServlet {

    private final UsuarioDao usuarioDao = new UsuarioDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession(false);
        if (sessao != null) {
            sessao.invalidate(); // destrói a sessão e tudo que havia nela
        }
        response.sendRedirect(request.getContextPath() + "/login.jsp?aviso=logout");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String nomeUsuario = valor(request.getParameter("nomeUsuario"));
        String senha = request.getParameter("senha");

        Usuario usuario = null;
        boolean senhaOk = false;
        if (!nomeUsuario.isEmpty() && senha != null && !senha.isEmpty()) {
            usuario = usuarioDao.buscarPorNomeUsuario(nomeUsuario);
            // Se o usuário não existe, verificarSenha recebe hash null e retorna false
            senhaOk = SegurancaUtil.verificarSenha(senha, usuario == null ? null : usuario.getSenhaHash());
        }

        if (!senhaOk) {
            // Mesma mensagem para "usuário não existe" e "senha errada": não revela qual dos dois falhou
            request.setAttribute("erro", "Usuário ou senha inválidos.");
            request.setAttribute("nomeUsuario", nomeUsuario);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        // Anti "session fixation": descarta qualquer sessão antiga e cria uma nova após autenticar
        HttpSession antiga = request.getSession(false);
        if (antiga != null) {
            antiga.invalidate();
        }
        HttpSession sessao = request.getSession(true);

        usuario.setSenhaHash(null); // o hash não precisa ficar na memória da sessão
        sessao.setAttribute(AuthFilter.ATRIBUTO_SESSAO, usuario);

        response.sendRedirect(request.getContextPath() + "/Home.jsp");
    }

    private static String valor(String s) {
        return s == null ? "" : s.trim();
    }
}
