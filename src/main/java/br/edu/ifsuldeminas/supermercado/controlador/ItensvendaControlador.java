package br.edu.ifsuldeminas.supermercado.controlador;

import br.edu.ifsuldeminas.supermercado.entidade.Itensvenda;
import br.edu.ifsuldeminas.supermercado.entidade.Produto;
import br.edu.ifsuldeminas.supermercado.entidade.Venda;
import br.edu.ifsuldeminas.supermercado.modelo.dao.ItensvendaDao;
import br.edu.ifsuldeminas.supermercado.modelo.dao.ProdutoDao;
import br.edu.ifsuldeminas.supermercado.modelo.dao.VendaDao;
import br.edu.ifsuldeminas.supermercado.servico.WebConstante;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(WebConstante.BASE_PATH + "/ItensvendaControlador")
public class ItensvendaControlador extends HttpServlet {

    String opcao = "", codigoItensvenda = "", codigoVenda = "", codigoProduto = "", quantidadeItensvenda = "", precoUnitarioItensvenda = "";

    Itensvenda objItensvenda = new Itensvenda();
    ItensvendaDao objItensvendaDao = new ItensvendaDao();
    ProdutoDao objProdutoDao = new ProdutoDao();
    VendaDao objVendaDao = new VendaDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try {
            opcao = request.getParameter("opcao");

            if (opcao == null || opcao.isEmpty()) {
                opcao = "cadastrar";
            }

            codigoItensvenda = request.getParameter("codigoItensvenda");
            codigoVenda = request.getParameter("codigoVenda");
            codigoProduto = request.getParameter("codigoProduto");
            quantidadeItensvenda = request.getParameter("quantidadeItensvenda");
            precoUnitarioItensvenda = request.getParameter("precoUnitarioItensvenda");

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void cadastrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Venda v = new Venda();
        v.setCodVenda(Integer.valueOf(codigoVenda));
        objItensvenda.setVenda(v);

        Produto p = new Produto();
        p.setCodigoProduto(Integer.valueOf(codigoProduto));
        objItensvenda.setProduto(p);

        objItensvenda.setQuantidade(Integer.valueOf(quantidadeItensvenda));
        objItensvenda.setPrecoUnitario(Double.valueOf(precoUnitarioItensvenda.replace(",", ".")));

        objItensvendaDao.salvar(objItensvenda);

        request.setAttribute("mensagem", "Item de venda cadastrado com sucesso!");
        encaminharPagina(request, response);
    }

    protected void encaminharPagina(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Itensvenda> listaItensvenda = objItensvendaDao.buscarTodos();
        request.setAttribute("listaItensvenda", listaItensvenda);

        List<Produto> listaProduto = objProdutoDao.buscarTodos();
        request.setAttribute("listaProduto", listaProduto);

        List<Venda> listaVenda = objVendaDao.buscarTodasVendas();
        request.setAttribute("listaVenda", listaVenda);

        RequestDispatcher rd = request.getRequestDispatcher("/CadastroItensvenda.jsp");
        rd.forward(request, response);
    }

    protected void enviarAlterar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("codigoItensvenda", codigoItensvenda);
        request.setAttribute("codigoVenda", codigoVenda);
        request.setAttribute("codigoProduto", codigoProduto);
        request.setAttribute("quantidadeItensvenda", quantidadeItensvenda);
        request.setAttribute("precoUnitarioItensvenda", precoUnitarioItensvenda);
        request.setAttribute("opcao", "executarAlterar");
        request.setAttribute("mensagem", "Edite os dados do item da venda e clique no botão de salvar.");

        encaminharPagina(request, response);
    }

    protected void executarAlterar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        objItensvenda.setId(Integer.valueOf(codigoItensvenda));

        Venda v = new Venda();
        v.setCodVenda(Integer.valueOf(codigoVenda));
        objItensvenda.setVenda(v);

        Produto p = new Produto();
        p.setCodigoProduto(Integer.valueOf(codigoProduto));
        objItensvenda.setProduto(p);

        objItensvenda.setQuantidade(Integer.valueOf(quantidadeItensvenda));
        objItensvenda.setPrecoUnitario(Double.valueOf(precoUnitarioItensvenda.replace(",", ".")));

        objItensvendaDao.alterar(objItensvenda);
        request.setAttribute("mensagem", "Item de venda alterado com sucesso!");

        encaminharPagina(request, response);
    }

    protected void enviarExcluir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("codigoItensvenda", codigoItensvenda);
        request.setAttribute("codigoVenda", codigoVenda);
        request.setAttribute("codigoProduto", codigoProduto);
        request.setAttribute("quantidadeItensvenda", quantidadeItensvenda);
        request.setAttribute("precoUnitarioItensvenda", precoUnitarioItensvenda);
        request.setAttribute("opcao", "executarExcluir");
        request.setAttribute("mensagem", "Clique em salvar para confirmar a exclusão.");

        encaminharPagina(request, response);
    }

    protected void executarExcluir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        objItensvenda.setId(Integer.valueOf(codigoItensvenda));
        objItensvendaDao.excluir(objItensvenda);
        request.setAttribute("mensagem", "Item de venda excluído com sucesso!");

        encaminharPagina(request, response);
    }

    protected void cancelar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("codigoItensvenda", "0");
        request.setAttribute("codigoVenda", "");
        request.setAttribute("codigoProduto", "");
        request.setAttribute("quantidadeItensvenda", "");
        request.setAttribute("precoUnitarioItensvenda", "");
        request.setAttribute("opcao", "cadastrar");

        encaminharPagina(request, response);
    }
}