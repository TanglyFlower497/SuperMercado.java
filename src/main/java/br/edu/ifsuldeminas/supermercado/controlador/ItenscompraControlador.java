/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.supermercado.controlador;

import br.edu.ifsuldeminas.supermercado.entidade.Itenscompra;
import br.edu.ifsuldeminas.supermercado.entidade.Produto;
import br.edu.ifsuldeminas.supermercado.entidade.Compra;
import br.edu.ifsuldeminas.supermercado.modelo.dao.ItenscompraDao;
import br.edu.ifsuldeminas.supermercado.modelo.dao.ProdutoDao;
import br.edu.ifsuldeminas.supermercado.modelo.dao.CompraDao;
import br.edu.ifsuldeminas.supermercado.servico.WebConstante;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
/**
 *
 * @author 11783374640
 */

@WebServlet(WebConstante.BASE_PATH + "/ItenscompraControlador")
public class ItenscompraControlador extends HttpServlet {

    String opcao = "", codigoItenscompra = "", codigoCompra = "", codigoProduto = "", quantidadeItenscompra = "", precoUnitarioItenscompra = "";

    Itenscompra objItenscompra = new Itenscompra();
    ItenscompraDao objItenscompraDao = new ItenscompraDao();
    ProdutoDao objProdutoDao = new ProdutoDao();
    CompraDao objCompraDao = new CompraDao();

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

            codigoItenscompra = request.getParameter("codigoItenscompra");
            codigoCompra = request.getParameter("codigoCompra");
            codigoProduto = request.getParameter("codigoProduto");
            quantidadeItenscompra = request.getParameter("quantidadeItenscompra");
            precoUnitarioItenscompra = request.getParameter("precoUnitarioItenscompra");

            switch (opcao) {
                case "abrir": encaminharPagina(request, response); break;
                case "cadastrar": cadastrar(request, response); break;
                case "enviarAlterar": enviarAlterar(request, response); break;
                case "executarAlterar": executarAlterar(request, response); break;
                case "enviarExcluir": enviarExcluir(request, response); break;
                case "executarExcluir": executarExcluir(request, response); break;
                case "cancelar": cancelar(request, response); break;
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

        Compra c = new Compra();
        c.setCodCompra(Integer.valueOf(codigoCompra));
        objItenscompra.setCompra(c);

        Produto p = new Produto();
        p.setCodigoProduto(Integer.valueOf(codigoProduto));
        objItenscompra.setProduto(p);

        objItenscompra.setQuantidade(Integer.valueOf(quantidadeItenscompra));
        objItenscompra.setPrecoUnitario(Double.valueOf(precoUnitarioItenscompra.replace(",", ".")));

        objItenscompraDao.salvar(objItenscompra);
        request.setAttribute("mensagem", "Item de compra cadastrado com sucesso!");
        encaminharPagina(request, response);
    }

    protected void encaminharPagina(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Itenscompra> listaItenscompra = objItenscompraDao.buscarTodos();
        request.setAttribute("listaItenscompra", listaItenscompra);

        List<Produto> listaProduto = objProdutoDao.buscarTodos();
        request.setAttribute("listaProduto", listaProduto);

        List<Compra> listaCompra = objCompraDao.buscarTodasCompras(); // Espera-se que este método exista no seu DAO de Compra
        request.setAttribute("listaCompra", listaCompra);

        RequestDispatcher rd = request.getRequestDispatcher("/CadastroItenscompra.jsp");
        rd.forward(request, response);
    }

    protected void enviarAlterar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("codigoItenscompra", codigoItenscompra);
        request.setAttribute("codigoCompra", codigoCompra);
        request.setAttribute("codigoProduto", codigoProduto);
        request.setAttribute("quantidadeItenscompra", quantidadeItenscompra);
        request.setAttribute("precoUnitarioItenscompra", precoUnitarioItenscompra);
        request.setAttribute("opcao", "executarAlterar");
        request.setAttribute("mensagem", "Edite os dados do item da compra e clique no botão de salvar.");

        encaminharPagina(request, response);
    }

    protected void executarAlterar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        objItenscompra.setId(Integer.valueOf(codigoItenscompra));

        Compra c = new Compra();
        c.setCodCompra(Integer.valueOf(codigoCompra));
        objItenscompra.setCompra(c);

        Produto p = new Produto();
        p.setCodigoProduto(Integer.valueOf(codigoProduto));
        objItenscompra.setProduto(p);

        objItenscompra.setQuantidade(Integer.valueOf(quantidadeItenscompra));
        objItenscompra.setPrecoUnitario(Double.valueOf(precoUnitarioItenscompra.replace(",", ".")));

        objItenscompraDao.alterar(objItenscompra);
        request.setAttribute("mensagem", "Item de compra alterado com sucesso!");
        encaminharPagina(request, response);
    }

    protected void enviarExcluir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("codigoItenscompra", codigoItenscompra);
        request.setAttribute("codigoCompra", codigoCompra);
        request.setAttribute("codigoProduto", codigoProduto);
        request.setAttribute("quantidadeItenscompra", quantidadeItenscompra);
        request.setAttribute("precoUnitarioItenscompra", precoUnitarioItenscompra);
        request.setAttribute("opcao", "executarExcluir");
        request.setAttribute("mensagem", "Clique em salvar para confirmar a exclusão.");

        encaminharPagina(request, response);
    }

    protected void executarExcluir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        objItenscompra.setId(Integer.valueOf(codigoItenscompra));
        objItenscompraDao.excluir(objItenscompra);
        request.setAttribute("mensagem", "Item de compra excluído com sucesso!");
        encaminharPagina(request, response);
    }

    protected void cancelar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("codigoItenscompra", "");
        request.setAttribute("codigoCompra", "");
        request.setAttribute("codigoProduto", "");
        request.setAttribute("quantidadeItenscompra", "");
        request.setAttribute("precoUnitarioItenscompra", "");
        request.setAttribute("opcao", "cadastrar");

        encaminharPagina(request, response);
    }
}