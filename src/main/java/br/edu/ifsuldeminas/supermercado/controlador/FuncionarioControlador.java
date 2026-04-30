package br.edu.ifsuldeminas.supermercado.controlador;

import br.edu.ifsuldeminas.supermercado.modelo.dao.FuncionarioDao;
import br.edu.ifsuldeminas.supermercado.entidade.Funcionario;
import br.edu.ifsuldeminas.supermercado.servico.WebConstante;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(WebConstante.BASE_PATH + "/FuncionarioControlador")
public class FuncionarioControlador extends HttpServlet {

    String codigoFuncionario = "", nomeFuncionario = "", cargoFuncionario = "", salarioFuncionario = "", opcao = "";

    Funcionario objFuncionario = new Funcionario();
    FuncionarioDao objFuncionarioDao = new FuncionarioDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            opcao = request.getParameter("opcao");

            if (opcao == null || opcao.isEmpty()) {
                opcao = "cadastrar";
            }

            codigoFuncionario = request.getParameter("codigoFuncionario");
            nomeFuncionario = request.getParameter("nomeFuncionario");
            cargoFuncionario = request.getParameter("cargoFuncionario");
            salarioFuncionario = request.getParameter("salarioFuncionario");

            switch (opcao) {

                case "cadastrar":
                    cadastrar(request, response);
                    break;

                case "enviarAlterar":
                    enviarAlterar(request, response);
                    break;

                case "executarAlterar":
                    executarAlterar(request, response);
                    break;

                case "enviarExcluir":
                    enviarExcluir(request, response);
                    break;

                case "executarExcluir":
                    executarExcluir(request, response);
                    break;

                case "cancelar":
                    cancelar(request, response);
                    break;

                default:
                    throw new IllegalArgumentException("Opção inválida: " + opcao);
            }

        } catch (NumberFormatException ex) {
            response.getWriter().println("Erro: valor numérico inválido. " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            response.getWriter().println("Erro: parâmetros inválidos. " + ex.getMessage());
        }
    }

    // ================= CADASTRAR =================
    protected void cadastrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        objFuncionario.setNomeFuncionario(nomeFuncionario);
        objFuncionario.setCargoFuncionario(cargoFuncionario);

        Double salario = (salarioFuncionario == null || salarioFuncionario.isEmpty())
                ? 0.0 : Double.parseDouble(salarioFuncionario);

        objFuncionario.setSalarioFuncionario(salario);

        objFuncionarioDao.salvar(objFuncionario);

        request.setAttribute("mensagem", "Funcionário cadastrado com sucesso");
        encaminharPagina(request, response);
    }

    // ================= LISTAR / ENCAMINHAR =================
    protected void encaminharPagina(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Funcionario> listaFuncionario = objFuncionarioDao.buscarTodos();
        request.setAttribute("listaFuncionario", listaFuncionario);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/CadastroFuncionario.jsp");
        dispatcher.forward(request, response);
    }

    // ================= ENVIAR ALTERAR =================
    protected void enviarAlterar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("codigoFuncionario", codigoFuncionario);
        request.setAttribute("nomeFuncionario", nomeFuncionario);
        request.setAttribute("cargoFuncionario", cargoFuncionario);
        request.setAttribute("salarioFuncionario", salarioFuncionario);
        request.setAttribute("opcao", "executarAlterar");
        request.setAttribute("mensagem", "Edite os dados e clique em salvar");

        encaminharPagina(request, response);
    }

    // ================= EXECUTAR ALTERAR =================
    protected void executarAlterar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        objFuncionario.setCodigoFuncionario(Integer.valueOf(codigoFuncionario));
        objFuncionario.setNomeFuncionario(nomeFuncionario);
        objFuncionario.setCargoFuncionario(cargoFuncionario);

        Double salario = (salarioFuncionario == null || salarioFuncionario.isEmpty())
                ? 0.0 : Double.parseDouble(salarioFuncionario);

        objFuncionario.setSalarioFuncionario(salario);

        objFuncionarioDao.alterar(objFuncionario);

        request.setAttribute("mensagem", "Funcionário alterado com sucesso");
        encaminharPagina(request, response);
    }

    // ================= ENVIAR EXCLUIR =================
    protected void enviarExcluir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("codigoFuncionario", codigoFuncionario);
        request.setAttribute("nomeFuncionario", nomeFuncionario);
        request.setAttribute("cargoFuncionario", cargoFuncionario);
        request.setAttribute("salarioFuncionario", salarioFuncionario);
        request.setAttribute("opcao", "executarExcluir");
        request.setAttribute("mensagem", "Clique em salvar para excluir");

        encaminharPagina(request, response);
    }

    // ================= EXECUTAR EXCLUIR =================
    protected void executarExcluir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        objFuncionario.setCodigoFuncionario(Integer.valueOf(codigoFuncionario));
        objFuncionarioDao.excluir(objFuncionario);

        request.setAttribute("mensagem", "Funcionário excluído com sucesso");
        encaminharPagina(request, response);
    }

    // ================= CANCELAR =================
    protected void cancelar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("codigoFuncionario", "0");
        request.setAttribute("nomeFuncionario", "");
        request.setAttribute("cargoFuncionario", "");
        request.setAttribute("salarioFuncionario", "");
        request.setAttribute("opcao", "cadastrar");

        encaminharPagina(request, response);
    }
}