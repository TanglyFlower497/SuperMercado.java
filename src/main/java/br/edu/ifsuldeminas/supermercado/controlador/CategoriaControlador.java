/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.supermercado.controlador;

/**
 *
 * @author vcart
 */
import br.edu.ifsuldeminas.supermercado.entidade.Categoria;
import br.edu.ifsuldeminas.supermercado.modelo.dao.CategoriaDao;
import br.edu.ifsuldeminas.supermercado.servico.WebConstante;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;


@WebServlet(WebConstante.BASE_PATH + "/CategoriaControlador")
public class CategoriaControlador extends HttpServlet {

    String codigoCategoria="", nomeCategoria="", descricaoCategoria="", opcao="";

    Categoria objCategoria = new Categoria();
    CategoriaDao objCategoriaDao = new CategoriaDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            opcao = request.getParameter("opcao");

            if(opcao == null || opcao.isEmpty()){
                opcao = "cadastrar";
            }

            codigoCategoria = request.getParameter("codigoCategoria");
            nomeCategoria = request.getParameter("nomeCategoria");
            descricaoCategoria = request.getParameter("descricaoCategoria");

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

        objCategoria.setNomeCategoria(nomeCategoria);
        objCategoria.setDescricaoCategoria(descricaoCategoria);

        objCategoriaDao.salvar(objCategoria);

        request.setAttribute("mensagem", "Categoria cadastrada!");
        encaminharPagina(request,response);
    }

    protected void encaminharPagina(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Categoria> lista = objCategoriaDao.buscarTodos();
        request.setAttribute("listaCategoria", lista);

        RequestDispatcher rd = request.getRequestDispatcher("/CadastroCategoria.jsp");
        rd.forward(request,response);
    }

    protected void enviarAlterar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("codigoCategoria", codigoCategoria);
        request.setAttribute("nomeCategoria", nomeCategoria);
        request.setAttribute("descricaoCategoria", descricaoCategoria);
        request.setAttribute("opcao", "executarAlterar");

        encaminharPagina(request,response);
    }

    protected void executarAlterar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        objCategoria.setCodigoCategoria(Integer.valueOf(codigoCategoria));
        objCategoria.setNomeCategoria(nomeCategoria);
        objCategoria.setDescricaoCategoria(descricaoCategoria);

        objCategoriaDao.alterar(objCategoria);

        encaminharPagina(request,response);
    }

    protected void enviarExcluir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("codigoCategoria", codigoCategoria);
        request.setAttribute("nomeCategoria", nomeCategoria);
        request.setAttribute("descricaoCategoria", descricaoCategoria);
        request.setAttribute("opcao", "executarExcluir");

        encaminharPagina(request,response);
    }

    protected void executarExcluir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        objCategoria.setCodigoCategoria(Integer.valueOf(codigoCategoria));
        objCategoriaDao.excluir(objCategoria);

        encaminharPagina(request,response);
    }

    protected void cancelar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("codigoCategoria", "0");
        request.setAttribute("nomeCategoria", "");
        request.setAttribute("descricaoCategoria", "");
        request.setAttribute("opcao", "cadastrar");

        encaminharPagina(request,response);
    }
}