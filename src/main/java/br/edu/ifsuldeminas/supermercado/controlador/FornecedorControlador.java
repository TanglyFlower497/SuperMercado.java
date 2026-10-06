package br.edu.ifsuldeminas.supermercado.controlador;

/**
 *
 * @author vcart
 */
import br.edu.ifsuldeminas.supermercado.entidade.Fornecedor;
import br.edu.ifsuldeminas.supermercado.modelo.dao.FornecedorDao;
import br.edu.ifsuldeminas.supermercado.servico.WebConstante;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(WebConstante.BASE_PATH + "/FornecedorControlador")
public class FornecedorControlador extends HttpServlet {

    String codigoFornecedor="", nomeFornecedor="", cnpjFornecedor="", telefoneFornecedor="", emailFornecedor="", opcao="";

    Fornecedor objFornecedor = new Fornecedor();
    FornecedorDao objFornecedorDao = new FornecedorDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
            
        try {
            opcao = request.getParameter("opcao");

            if (opcao == null || opcao.isEmpty()) {
                opcao = "cadastrar";
            }

            codigoFornecedor = request.getParameter("codigoFornecedor");
            nomeFornecedor = request.getParameter("nomeFornecedor");
            cnpjFornecedor = request.getParameter("cnpjFornecedor");
            telefoneFornecedor = request.getParameter("telefoneFornecedor");
            emailFornecedor = request.getParameter("emailFornecedor");

            switch (opcao) {
                 case "abrir": 
                    encaminharPagina(request, response);
                    break;

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
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void cadastrar(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
            
        objFornecedor.setNomeFornecedor(nomeFornecedor);
        objFornecedor.setCnpjFornecedor(cnpjFornecedor);
        objFornecedor.setTelefoneFornecedor(telefoneFornecedor);
        objFornecedor.setEmailFornecedor(emailFornecedor);
        
        objFornecedorDao.salvar(objFornecedor);
        
        request.setAttribute("mensagem", "Fornecedor cadastrado!");
        encaminharPagina(request, response);
    }

    protected void encaminharPagina(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
            
        List<Fornecedor> lista = objFornecedorDao.buscarTodos();
        request.setAttribute("listaFornecedor", lista);
        
        RequestDispatcher rd = request.getRequestDispatcher("/CadastroFornecedor.jsp");
        rd.forward(request, response);
    }
    
    protected void enviarAlterar(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
            
        request.setAttribute("codigoFornecedor", codigoFornecedor);
        request.setAttribute("nomeFornecedor", nomeFornecedor);
        request.setAttribute("cnpjFornecedor", cnpjFornecedor);
        request.setAttribute("telefoneFornecedor", telefoneFornecedor);
        request.setAttribute("emailFornecedor", emailFornecedor);
        request.setAttribute("opcao", "executarAlterar");
        request.setAttribute("mensagem","Edite os dados e clique no botão de salvar.");
        
        encaminharPagina(request, response);
    }

    protected void executarAlterar(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
            
        objFornecedor.setCodigoFornecedor(Integer.valueOf(codigoFornecedor));
        objFornecedor.setNomeFornecedor(nomeFornecedor);
        objFornecedor.setCnpjFornecedor(cnpjFornecedor);
        objFornecedor.setTelefoneFornecedor(telefoneFornecedor);
        objFornecedor.setEmailFornecedor(emailFornecedor);
        
        objFornecedorDao.alterar(objFornecedor);
        request.setAttribute("mensagem", "Fornecedor Alterado com sucesso!");
        
        encaminharPagina(request, response);
    }

    protected void enviarExcluir(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
            
        request.setAttribute("codigoFornecedor", codigoFornecedor);
        request.setAttribute("nomeFornecedor", nomeFornecedor);
        request.setAttribute("cnpjFornecedor", cnpjFornecedor);
        request.setAttribute("telefoneFornecedor", telefoneFornecedor);
        request.setAttribute("emailFornecedor", emailFornecedor);
        request.setAttribute("opcao", "executarExcluir");
        request.setAttribute("mensagem", "Clique em salvar para excluir");
        
        encaminharPagina(request, response);
    }
    
    protected void executarExcluir(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
            
        objFornecedor.setCodigoFornecedor(Integer.valueOf(codigoFornecedor));
        objFornecedorDao.excluir(objFornecedor);
        request.setAttribute("mensagem", "Fornecedor excluido com sucesso");
        
        encaminharPagina(request, response);
    }

    protected void cancelar(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
            
        request.setAttribute("codigoFornecedor", "0");
        request.setAttribute("nomeFornecedor", "");
        request.setAttribute("cnpjFornecedor", "");
        request.setAttribute("telefoneFornecedor", "");
        request.setAttribute("emailFornecedor", "");
        request.setAttribute("opcao", "cadastrar");
        
        encaminharPagina(request, response);
    }
}