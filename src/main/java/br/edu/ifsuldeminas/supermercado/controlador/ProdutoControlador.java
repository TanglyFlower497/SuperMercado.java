package br.edu.ifsuldeminas.supermercado.controlador;

import br.edu.ifsuldeminas.supermercado.entidade.Categoria;
import br.edu.ifsuldeminas.supermercado.entidade.Produto;
import br.edu.ifsuldeminas.supermercado.modelo.dao.CategoriaDao;
import br.edu.ifsuldeminas.supermercado.modelo.dao.ProdutoDao;
import br.edu.ifsuldeminas.supermercado.servico.WebConstante;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet(WebConstante.BASE_PATH + "/ProdutoControlador")
public class ProdutoControlador extends HttpServlet {

    String opcao="", codigoProduto="", nomeProduto="", codigoBarrasProduto="", precoProduto="", custoProduto="", estoqueProduto="", estoqueMinimoProduto="", validadeProduto="", marcaProduto="", produtoCategoria="";

    Produto objProduto = new Produto();
    ProdutoDao objProdutoDao = new ProdutoDao();
    CategoriaDao objCategoriaDao = new CategoriaDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try {
            opcao = request.getParameter("opcao");

            if(opcao == null || opcao.isEmpty()){
                opcao = "cadastrar";
            }

            codigoProduto = request.getParameter("codigoProduto");
            nomeProduto = request.getParameter("nomeProduto");
            codigoBarrasProduto = request.getParameter("codigoBarrasProduto");
            precoProduto = request.getParameter("precoProduto");
            custoProduto = request.getParameter("custoProduto");
            estoqueProduto = request.getParameter("estoqueProduto");
            estoqueMinimoProduto = request.getParameter("estoqueMinimoProduto");
            validadeProduto = request.getParameter("validadeProduto");
            marcaProduto = request.getParameter("marcaProduto");
            produtoCategoria = request.getParameter("produtoCategoria");

            switch(opcao){
                 case "abrir": 
                    encaminharPagina(request, response);
                    break;

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

        objProduto.setNomeProduto(nomeProduto);
        objProduto.setCodigoBarrasProduto(codigoBarrasProduto);
        objProduto.setPrecoProduto(Double.valueOf(precoProduto.replace(",", ".")));
        objProduto.setCustoProduto(Double.valueOf(custoProduto.replace(",", ".")));
        objProduto.setEstoqueProduto(Integer.valueOf(estoqueProduto));
        objProduto.setEstoqueMinimoProduto(Integer.valueOf(estoqueMinimoProduto));
        objProduto.setValidadeProduto(Date.valueOf(validadeProduto));
        objProduto.setMarcaProduto(marcaProduto);
        objProduto.getCategoria().setCodigoCategoria(Integer.valueOf(produtoCategoria));

        objProdutoDao.salvar(objProduto);

        request.setAttribute("mensagem", "Produto cadastrado!");
        encaminharPagina(request,response);
    }

    protected void encaminharPagina(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Produto> listaProduto = objProdutoDao.buscarTodos();
        request.setAttribute("listaProduto", listaProduto);

        List<Categoria> listaCategoria = objCategoriaDao.buscarTodos();
        request.setAttribute("listaCategoria", listaCategoria);

        RequestDispatcher rd = request.getRequestDispatcher("/CadastroProduto.jsp");
        rd.forward(request,response);
    }

    protected void enviarAlterar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("codigoProduto", codigoProduto);
        request.setAttribute("nomeProduto", nomeProduto);
        request.setAttribute("codigoBarrasProduto", codigoBarrasProduto);
        request.setAttribute("precoProduto", precoProduto);
        request.setAttribute("custoProduto", custoProduto);
        request.setAttribute("estoqueProduto", estoqueProduto);
        request.setAttribute("estoqueMinimoProduto", estoqueMinimoProduto);
        request.setAttribute("validadeProduto", validadeProduto);
        request.setAttribute("marcaProduto", marcaProduto);
        request.setAttribute("produtoCategoria", produtoCategoria);
        request.setAttribute("opcao", "executarAlterar");
        request.setAttribute("mensagem","Edite os dados e clique no botão de salvar.");

        encaminharPagina(request,response);
    }

    protected void executarAlterar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        objProduto.setCodigoProduto(Integer.valueOf(codigoProduto));
        objProduto.setNomeProduto(nomeProduto);
        objProduto.setCodigoBarrasProduto(codigoBarrasProduto);
        objProduto.setPrecoProduto(Double.valueOf(precoProduto.replace(",", ".")));
        objProduto.setCustoProduto(Double.valueOf(custoProduto.replace(",", ".")));
        objProduto.setEstoqueProduto(Integer.valueOf(estoqueProduto));
        objProduto.setEstoqueMinimoProduto(Integer.valueOf(estoqueMinimoProduto));
        objProduto.setValidadeProduto(Date.valueOf(validadeProduto));
        objProduto.setMarcaProduto(marcaProduto);
        objProduto.getCategoria().setCodigoCategoria(Integer.valueOf(produtoCategoria));

        objProdutoDao.alterar(objProduto);
        request.setAttribute("mensagem", "Produto alterado com sucesso");

        encaminharPagina(request,response);
    }

    protected void enviarExcluir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("codigoProduto", codigoProduto);
        request.setAttribute("nomeProduto", nomeProduto);
        request.setAttribute("codigoBarrasProduto", codigoBarrasProduto);
        request.setAttribute("precoProduto", precoProduto);
        request.setAttribute("custoProduto", custoProduto);
        request.setAttribute("estoqueProduto", estoqueProduto);
        request.setAttribute("estoqueMinimoProduto", estoqueMinimoProduto);
        request.setAttribute("validadeProduto", validadeProduto);
        request.setAttribute("marcaProduto", marcaProduto);
        request.setAttribute("produtoCategoria", produtoCategoria);
        request.setAttribute("opcao", "executarExcluir");
        request.setAttribute("mensagem", "Clique em salvar para excluir");

        encaminharPagina(request,response);
    }

    protected void executarExcluir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        objProduto.setCodigoProduto(Integer.valueOf(codigoProduto));
        objProdutoDao.excluir(objProduto);
        request.setAttribute("mensagem", "Produto excluído com sucesso");

        encaminharPagina(request,response);
    }

    protected void cancelar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("codigoProduto", "0");
        request.setAttribute("nomeProduto", "");
        request.setAttribute("codigoBarrasProduto", "");
        request.setAttribute("precoProduto", "");
        request.setAttribute("custoProduto", "");
        request.setAttribute("estoqueProduto", "");
        request.setAttribute("estoqueMinimoProduto", "");
        request.setAttribute("validadeProduto", "");
        request.setAttribute("marcaProduto", "");
        request.setAttribute("produtoCategoria", "");
        request.setAttribute("opcao", "cadastrar");

        encaminharPagina(request,response);
    }
}