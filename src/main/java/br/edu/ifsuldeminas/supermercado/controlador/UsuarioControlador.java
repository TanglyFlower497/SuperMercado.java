package br.edu.ifsuldeminas.supermercado.controlador;

import br.edu.ifsuldeminas.supermercado.entidade.Usuario;
import br.edu.ifsuldeminas.supermercado.modelo.dao.UsuarioDao;
import br.edu.ifsuldeminas.supermercado.servico.SegurancaUtil;
import br.edu.ifsuldeminas.supermercado.servico.WebConstante;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

/** Cadastro de novos usuários. A senha é transformada em hash BCrypt antes de ir ao banco. */
@WebServlet(WebConstante.BASE_PATH + "/UsuarioControlador")
public class UsuarioControlador extends HttpServlet {

    private static final Pattern NOME_USUARIO = Pattern.compile("^[A-Za-z0-9_.-]{3,50}$");
    private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private final UsuarioDao usuarioDao = new UsuarioDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/CadastrarUsuario.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String nomeUsuario = valor(request.getParameter("nomeUsuario"));
        String email = valor(request.getParameter("email"));
        String senha = request.getParameter("senha");
        String confirmaSenha = request.getParameter("confirmaSenha");

        String erro = validar(nomeUsuario, email, senha, confirmaSenha);

        if (erro == null) {
            boolean nomeEmUso = usuarioDao.buscarPorNomeUsuario(nomeUsuario) != null;
            boolean emailEmUso = usuarioDao.buscarPorEmail(email) != null;
            if (nomeEmUso || emailEmUso) {
                erro = "Não foi possível cadastrar: nome de usuário ou e-mail já está em uso.";
            }
        }

        if (erro == null) {
            Usuario novo = new Usuario();
            novo.setNomeUsuario(nomeUsuario);
            novo.setEmail(email);
            novo.setSenhaHash(SegurancaUtil.gerarHashSenha(senha)); // só o hash vai para o banco
            if (!usuarioDao.salvar(novo)) {
                erro = "Não foi possível cadastrar: nome de usuário ou e-mail já está em uso.";
            }
        }

        if (erro != null) {
            request.setAttribute("erro", erro);
            request.setAttribute("nomeUsuario", nomeUsuario); // devolve o que foi digitado (menos a senha)
            request.setAttribute("email", email);
            request.getRequestDispatcher("/CadastrarUsuario.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/login.jsp?aviso=cadastrado");
    }

    private String validar(String nomeUsuario, String email, String senha, String confirmaSenha) {
        if (!NOME_USUARIO.matcher(nomeUsuario).matches()) {
            return "O nome de usuário deve ter de 3 a 50 caracteres: letras, números, ponto, hífen ou sublinhado.";
        }
        if (email.length() > 255 || !EMAIL.matcher(email).matches()) {
            return "Informe um e-mail válido.";
        }
        return SegurancaUtil.validarSenha(senha, confirmaSenha);
    }

    private static String valor(String s) {
        return s == null ? "" : s.trim();
    }
}
