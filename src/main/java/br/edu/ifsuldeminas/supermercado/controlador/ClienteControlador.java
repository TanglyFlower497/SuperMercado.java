/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.supermercado.controlador;

/**
 *
 * @author vcart
 */
import br.edu.ifsuldeminas.supermercado.entidade.Cliente;
import br.edu.ifsuldeminas.supermercado.modelo.dao.ClienteDao;
import br.edu.ifsuldeminas.supermercado.servico.WebConstante;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(WebConstante.BASE_PATH + "/ClienteControlador")
public class ClienteControlador extends HttpServlet {

    String codigoCliente="", nomeCliente="", cpfCliente="", telefoneCliente="", emailCliente="", opcao="";

    Cliente objCliente = new Cliente();
    ClienteDao objClienteDao = new ClienteDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            opcao = request.getParameter("opcao");

            if(opcao == null || opcao.isEmpty()){
                opcao = "cadastrar";
            }

            codigoCliente = request.getParameter("codigoCliente");
            nomeCliente = request.getParameter("nomeCliente");
            cpfCliente = request.getParameter("cpfCliente");
            telefoneCliente = request.getParameter("telefoneCliente");
            emailCliente = request.getParameter("emailCliente");

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

        objCliente.setNomeCliente(nomeCliente);
        objCliente.setCpfCliente(cpfCliente);
        objCliente.setTelefoneCliente(telefoneCliente);
        objCliente.setEmailCliente(emailCliente);

        objClienteDao.salvar(objCliente);

        request.setAttribute("mensagem", "Cliente cadastrado!");
        encaminharPagina(request,response);
    }

    protected void encaminharPagina(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Cliente> lista = objClienteDao.buscarTodos();
        request.setAttribute("listaCliente", lista);

        RequestDispatcher rd = request.getRequestDispatcher("/CadastroCliente.jsp");
        rd.forward(request,response);
    }

    protected void enviarAlterar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("codigoCliente", codigoCliente);
        request.setAttribute("nomeCliente", nomeCliente);
        request.setAttribute("cpfCliente", cpfCliente);
        request.setAttribute("telefoneCliente", telefoneCliente);
        request.setAttribute("emailCliente", emailCliente);
        request.setAttribute("opcao", "executarAlterar");

        encaminharPagina(request,response);
    }

    protected void executarAlterar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        objCliente.setCodigoCliente(Integer.valueOf(codigoCliente));
        objCliente.setNomeCliente(nomeCliente);
        objCliente.setCpfCliente(cpfCliente);
        objCliente.setTelefoneCliente(telefoneCliente);
        objCliente.setEmailCliente(emailCliente);

        objClienteDao.alterar(objCliente);

        encaminharPagina(request,response);
    }

    protected void enviarExcluir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("codigoCliente", codigoCliente);
        request.setAttribute("nomeCliente", nomeCliente);
        request.setAttribute("cpfCliente", cpfCliente);
        request.setAttribute("telefoneCliente", telefoneCliente);
        request.setAttribute("emailCliente", emailCliente);
        request.setAttribute("opcao", "executarExcluir");

        encaminharPagina(request,response);
    }

    protected void executarExcluir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        objCliente.setCodigoCliente(Integer.valueOf(codigoCliente));
        objClienteDao.excluir(objCliente);

        encaminharPagina(request,response);
    }

    protected void cancelar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("codigoCliente", "0");
        request.setAttribute("nomeCliente", "");
        request.setAttribute("cpfCliente", "");
        request.setAttribute("telefoneCliente", "");
        request.setAttribute("emailCliente", "");
        request.setAttribute("opcao", "cadastrar");

        encaminharPagina(request,response);
    }
}