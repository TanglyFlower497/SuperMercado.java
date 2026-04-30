/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.supermercado.controlador;

/**
 *
 * @author vcart
 */
import br.edu.ifsuldeminas.supermercado.entidade.Fornecedor;
import br.edu.ifsuldeminas.supermercado.modelo.dao.FornecedorDao;
import br.edu.ifsuldeminas.supermercado.servico.WebConstante;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(WebConstante.BASE_PATH + "/FornecedorControlador")
public class FornecedorControlador extends HttpServlet {

    private FornecedorDao objFornecedorDao = new FornecedorDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Variáveis locais para evitar conflitos entre usuários
            String opcao = request.getParameter("opcao");
            if (opcao == null || opcao.isEmpty()) {
                opcao = "cadastrar";
            }

            // Captura de parâmetros
            String codigo = request.getParameter("codigoFornecedor");
            String nome = request.getParameter("nomeFornecedor");
            String cnpj = request.getParameter("cnpjFornecedor");
            String telefone = request.getParameter("telefoneFornecedor");
            String email = request.getParameter("emailFornecedor");

            switch (opcao) {
                case "cadastrar":
                    cadastrar(request, response, nome, cnpj, telefone, email);
                    break;
                case "enviarAlterar":
                    enviarAlterar(request, response, codigo, nome, cnpj, telefone, email);
                    break;
                case "executarAlterar":
                    executarAlterar(request, response, codigo, nome, cnpj, telefone, email);
                    break;
                case "enviarExcluir":
                    enviarExcluir(request, response, codigo, nome, cnpj, telefone, email);
                    break;
                case "executarExcluir":
                    executarExcluir(request, response, codigo);
                    break;
                case "cancelar":
                    cancelar(request, response);
                    break;
                default:
                    encaminharPagina(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno: " + e.getMessage());
        }
    }

    protected void cadastrar(HttpServletRequest request, HttpServletResponse response, String nome, String cnpj, String tel, String email) throws ServletException, IOException {
        Fornecedor f = new Fornecedor();
        f.setNomeFornecedor(nome);
        f.setCnpjFornecedor(cnpj);
        f.setTelefoneFornecedor(tel);
        f.setEmailFornecedor(email);
        
        objFornecedorDao.salvar(f);
        request.setAttribute("mensagem", "Fornecedor cadastrado com sucesso!");
        encaminharPagina(request, response);
    }

    protected void executarAlterar(HttpServletRequest request, HttpServletResponse response, String codigo, String nome, String cnpj, String tel, String email) throws ServletException, IOException {
        if (codigo != null && !codigo.isEmpty() && !codigo.equals("0")) {
            Fornecedor f = new Fornecedor();
            f.setCodigoFornecedor(Integer.parseInt(codigo));
            f.setNomeFornecedor(nome);
            f.setCnpjFornecedor(cnpj);
            f.setTelefoneFornecedor(tel);
            f.setEmailFornecedor(email);
            
            objFornecedorDao.alterar(f);
            request.setAttribute("mensagem", "Fornecedor atualizado!");
        }
        encaminharPagina(request, response);
    }

    protected void executarExcluir(HttpServletRequest request, HttpServletResponse response, String codigo) throws ServletException, IOException {
        if (codigo != null && !codigo.isEmpty()) {
            Fornecedor f = new Fornecedor();
            f.setCodigoFornecedor(Integer.parseInt(codigo));
            objFornecedorDao.excluir(f);
            request.setAttribute("mensagem", "Fornecedor excluído!");
        }
        encaminharPagina(request, response);
    }

    protected void enviarAlterar(HttpServletRequest request, HttpServletResponse response, String codigo, String nome, String cnpj, String tel, String email) throws ServletException, IOException {
        request.setAttribute("codigoFornecedor", codigo);
        request.setAttribute("nomeFornecedor", nome);
        request.setAttribute("cnpjFornecedor", cnpj);
        request.setAttribute("telefoneFornecedor", tel);
        request.setAttribute("emailFornecedor", email);
        request.setAttribute("opcao", "executarAlterar");
        encaminharPagina(request, response);
    }

    protected void enviarExcluir(HttpServletRequest request, HttpServletResponse response, String codigo, String nome, String cnpj, String tel, String email) throws ServletException, IOException {
        request.setAttribute("codigoFornecedor", codigo);
        request.setAttribute("nomeFornecedor", nome);
        request.setAttribute("opcao", "executarExcluir");
        encaminharPagina(request, response);
    }

    protected void cancelar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("opcao", "cadastrar");
        encaminharPagina(request, response);
    }

    protected void encaminharPagina(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Fornecedor> lista = objFornecedorDao.buscarTodos();
        request.setAttribute("listaFornecedor", lista);
        RequestDispatcher rd = request.getRequestDispatcher("/CadastroFornecedor.jsp");
        rd.forward(request, response);
    }
}
