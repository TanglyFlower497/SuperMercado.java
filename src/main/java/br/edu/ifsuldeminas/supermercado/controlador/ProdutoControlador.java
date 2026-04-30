/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.supermercado.controlador;

/**
 *
 * @author vcart
 */


import br.edu.ifsuldeminas.supermercado.entidade.Produto;
import br.edu.ifsuldeminas.supermercado.modelo.dao.ProdutoDao;
import br.edu.ifsuldeminas.supermercado.modelo.dao.CategoriaDao;
import br.edu.ifsuldeminas.supermercado.servico.WebConstante;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(WebConstante.BASE_PATH + "/ProdutoControlador")
public class ProdutoControlador extends HttpServlet {

    String codigoProduto="", nomeProduto="", precoProduto="", estoqueProduto="", codigoCategoria="", opcao="";

    Produto objProduto = new Produto();
    ProdutoDao objProdutoDao = new ProdutoDao();
    CategoriaDao categoriaDao = new CategoriaDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            opcao = request.getParameter("opcao");

            if(opcao == null || opcao.isEmpty()){
                opcao = "cadastrar";
            }

            codigoProduto = request.getParameter("codigoProduto");
            nomeProduto = request.getParameter("nomeProduto");
            precoProduto = request.getParameter("precoProduto");
            estoqueProduto = request.getParameter("estoqueProduto");
            codigoCategoria = request.getParameter("codigoCategoria");

            switch(opcao){

                case "cadastrar":
                    cadastrar(request,response);
                    break;

                case "enviarAlterar":
                    enviarAlterar(request,response);
                    break;

                case "executarAlterar":
                    executarAlterar(request,response);
                    break;

                case "enviarExcluir":
                    enviarExcluir(request,response);
                    break;

                case "executarExcluir":
                    executarExcluir(request,response);
                    break;

                case "cancelar":
                    cancelar(request,response);
                    break;
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    protected void cadastrar(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    try {
        objProduto.setNomeProduto(nomeProduto);

        Double preco = Double.parseDouble(precoProduto);
        Integer estoque = Integer.parseInt(estoqueProduto);

        if (codigoCategoria == null || codigoCategoria.isEmpty()) {
            throw new IllegalArgumentException("Selecione uma categoria");
        }

        objProduto.setPrecoProduto(preco);
        objProduto.setEstoqueProduto(estoque);
        objProduto.setCodigoCategoria(Integer.parseInt(codigoCategoria));

        objProdutoDao.salvar(objProduto);

        request.setAttribute("mensagem", "Produto cadastrado com sucesso!");

    } catch (Exception e) {
        request.setAttribute("mensagem", "Erro ao salvar: " + e.getMessage());
    }

    encaminharPagina(request, response);
}

    protected void encaminharPagina(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
     //Verificar lista categoria
       List<Produto> lista = objProdutoDao.buscarTodos();
       
       request.setAttribute("listaProduto", lista);
       request.setAttribute("listaCategoria", categoriaDao.buscarTodos());

       RequestDispatcher rd = request.getRequestDispatcher("/CadastroProduto.jsp");
       rd.forward(request,response);
}

    protected void enviarAlterar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("codigoProduto", codigoProduto);
        request.setAttribute("nomeProduto", nomeProduto);
        request.setAttribute("precoProduto", precoProduto);
        request.setAttribute("estoqueProduto", estoqueProduto);
        request.setAttribute("codigoCategoria", codigoCategoria);
        request.setAttribute("opcao", "executarAlterar");

        encaminharPagina(request,response);
    }

    protected void executarAlterar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        objProduto.setCodigoProduto(Integer.valueOf(codigoProduto));
        objProduto.setNomeProduto(nomeProduto);
        objProduto.setPrecoProduto(Double.parseDouble(precoProduto));
        objProduto.setEstoqueProduto(Integer.parseInt(estoqueProduto));
        objProduto.setCodigoCategoria(Integer.parseInt(codigoCategoria));

        objProdutoDao.alterar(objProduto);

        encaminharPagina(request,response);
    }

    protected void enviarExcluir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("codigoProduto", codigoProduto);
        request.setAttribute("opcao", "executarExcluir");

        encaminharPagina(request,response);
    }

    protected void executarExcluir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        objProduto.setCodigoProduto(Integer.valueOf(codigoProduto));
        objProdutoDao.excluir(objProduto);

        encaminharPagina(request,response);
    }

    protected void cancelar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("codigoProduto", "0");
        request.setAttribute("nomeProduto", "");
        request.setAttribute("precoProduto", "");
        request.setAttribute("estoqueProduto", "");
        request.setAttribute("codigoCategoria", "");
        request.setAttribute("opcao", "cadastrar");

        encaminharPagina(request,response);
    }
}