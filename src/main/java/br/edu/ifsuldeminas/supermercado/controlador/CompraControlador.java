package br.edu.ifsuldeminas.supermercado.controlador;

import br.edu.ifsuldeminas.supermercado.entidade.Compra;
import br.edu.ifsuldeminas.supermercado.entidade.Fornecedor;
import br.edu.ifsuldeminas.supermercado.modelo.dao.CompraDao;
import br.edu.ifsuldeminas.supermercado.modelo.dao.FornecedorDao;
import br.edu.ifsuldeminas.supermercado.servico.WebConstante;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
/**
 *
 * @author 12421698650
 */
@WebServlet(WebConstante.BASE_PATH + "/CompraControlador")
public class CompraControlador extends HttpServlet {

    String opcao="", codCompra="", data="", total="", observacao="", compraFornecedor="";

    Compra objCompra = new Compra();
    CompraDao objCompraDao = new CompraDao();
    FornecedorDao objFornecedorDao = new FornecedorDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            opcao = request.getParameter("opcao");

            if(opcao == null || opcao.isEmpty()){
                opcao = "cadastrar";
            }

            codCompra = request.getParameter("codCompra");
            data = request.getParameter("data");
            total = request.getParameter("total");
            observacao = request.getParameter("observacao");
            compraFornecedor = request.getParameter("compraFornecedor");

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

        objCompra.setData(parseDate(data));
        objCompra.setTotal(Double.valueOf(total.replace(",", ".")));
        objCompra.setObservacao(observacao);
        objCompra.getFornecedor().setCodigoFornecedor(Integer.valueOf(compraFornecedor));

        objCompraDao.salvar(objCompra);

        request.setAttribute("mensagem", "Compra cadastrada com sucesso!");
        encaminharPagina(request, response);
    }

    protected void encaminharPagina(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Compra> listaCompra = objCompraDao.buscarTodasCompras();
        request.setAttribute("listaCompra", listaCompra);

        List<Fornecedor> listaFornecedor = objFornecedorDao.buscarTodos();
        request.setAttribute("listaFornecedor", listaFornecedor);

        RequestDispatcher rd = request.getRequestDispatcher("/CadastroCompra.jsp");
        rd.forward(request, response);
    }

    protected void enviarAlterar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
        request.setAttribute("codCompra", codCompra);
        request.setAttribute("data", data);
        request.setAttribute("total", total);
        request.setAttribute("observacao", observacao);
        request.setAttribute("compraFornecedor", compraFornecedor);
        request.setAttribute("opcao", "executarAlterar");
        request.setAttribute("mensagem","Edite os dados e clique no botão de salvar.");

        encaminharPagina(request, response);
    }

    protected void executarAlterar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        objCompra.setCodCompra(Integer.valueOf(codCompra));
        objCompra.setData(parseDate(data));
        objCompra.setTotal(Double.valueOf(total.replace(",", ".")));
        objCompra.setObservacao(observacao);
        objCompra.getFornecedor().setCodigoFornecedor(Integer.valueOf(compraFornecedor));
        objCompraDao.alterar(objCompra);
        request.setAttribute("mensagem", "Compra Alterada com sucesso");

        request.setAttribute("opcao", "cadastrar");
        encaminharPagina(request, response);
    }

    protected void enviarExcluir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("codCompra", codCompra);
        request.setAttribute("data", data);
        request.setAttribute("total", total);
        request.setAttribute("observacao", observacao);
        request.setAttribute("compraFornecedor", compraFornecedor);
        request.setAttribute("opcao", "executarExcluir");
        request.setAttribute("mensagem", "Clique em salvar para excluir");

        encaminharPagina(request, response);
    }

    protected void executarExcluir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        objCompra.setCodCompra(Integer.valueOf(codCompra));
        objCompraDao.excluir(objCompra);

        request.setAttribute("opcao", "cadastrar");
        request.setAttribute("mensagem", "Compra excluída com sucesso");
        encaminharPagina(request, response);
    }

    protected void cancelar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("codCompra", "0");
        request.setAttribute("data", "");
        request.setAttribute("total", "");
        request.setAttribute("observacao", "");
        request.setAttribute("compraFornecedor", "");
        request.setAttribute("opcao", "cadastrar");

        encaminharPagina(request, response);
    }

    private Date parseDate(String valor) {
        if (valor == null || valor.isEmpty()) {
            return null;
        }
        return Date.valueOf(valor);
    }
}