/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.supermercado.controlador;

/**
 *
 * @author vcart
 */

import br.edu.ifsuldeminas.supermercado.entidade.FormaPagamento;
import br.edu.ifsuldeminas.supermercado.modelo.dao.FormaPagamentoDao;
import br.edu.ifsuldeminas.supermercado.servico.WebConstante;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(WebConstante.BASE_PATH + "/FormaPagamentoControlador")
public class FormaPagamentoControlador extends HttpServlet {

    String codigoFormaPagamento="", descricaoFormaPagamento="", opcao="";

    FormaPagamento obj = new FormaPagamento();
    FormaPagamentoDao dao = new FormaPagamentoDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            opcao = request.getParameter("opcao");

            if(opcao == null || opcao.isEmpty()){
                opcao = "cadastrar";
            }

            codigoFormaPagamento = request.getParameter("codigoFormaPagamento");
            descricaoFormaPagamento = request.getParameter("descricaoFormaPagamento");

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

        obj.setDescricaoFormaPagamento(descricaoFormaPagamento);

        dao.salvar(obj);

        request.setAttribute("mensagem", "Forma de pagamento cadastrada!");
        encaminharPagina(request,response);
    }

    protected void encaminharPagina(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<FormaPagamento> lista = dao.buscarTodos();
        request.setAttribute("listaFormaPagamento", lista);

        RequestDispatcher rd = request.getRequestDispatcher("/CadastroFormaPagamento.jsp");
        rd.forward(request,response);
    }

    protected void enviarAlterar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("codigoFormaPagamento", codigoFormaPagamento);
        request.setAttribute("descricaoFormaPagamento", descricaoFormaPagamento);
        request.setAttribute("opcao", "executarAlterar");

        encaminharPagina(request,response);
    }

    protected void executarAlterar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        obj.setCodigoFormaPagamento(Integer.valueOf(codigoFormaPagamento));
        obj.setDescricaoFormaPagamento(descricaoFormaPagamento);

        dao.alterar(obj);

        encaminharPagina(request,response);
    }

    protected void enviarExcluir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("codigoFormaPagamento", codigoFormaPagamento);
        request.setAttribute("opcao", "executarExcluir");

        encaminharPagina(request,response);
    }

    protected void executarExcluir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        obj.setCodigoFormaPagamento(Integer.valueOf(codigoFormaPagamento));
        dao.excluir(obj);

        encaminharPagina(request,response);
    }

    protected void cancelar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("codigoFormaPagamento", "0");
        request.setAttribute("descricaoFormaPagamento", "");
        request.setAttribute("opcao", "cadastrar");

        encaminharPagina(request,response);
    }
}