/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.supermercado.controlador;
        
import br.edu.ifsuldeminas.supermercado.entidade.Venda;
import br.edu.ifsuldeminas.supermercado.entidade.Cliente;
import br.edu.ifsuldeminas.supermercado.entidade.Funcionario;
import br.edu.ifsuldeminas.supermercado.entidade.FormaPagamento;
import br.edu.ifsuldeminas.supermercado.modelo.dao.VendaDao;
import br.edu.ifsuldeminas.supermercado.modelo.dao.ClienteDao;
import br.edu.ifsuldeminas.supermercado.modelo.dao.FuncionarioDao;
import br.edu.ifsuldeminas.supermercado.modelo.dao.FormaPagamentoDao;
import br.edu.ifsuldeminas.supermercado.servico.WebConstante;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
/**
 *
 * @author vcart
 */



@WebServlet(WebConstante.BASE_PATH + "/VendaControlador")
public class VendaControlador extends HttpServlet {

    VendaDao objVendaDao = new VendaDao();
    ClienteDao objClienteDao = new ClienteDao();
    FuncionarioDao objFuncionarioDao = new FuncionarioDao();
    FormaPagamentoDao objFormaPagamentoDao = new FormaPagamentoDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String opcao = request.getParameter("opcao");
            
            if(opcao == null || opcao.isEmpty()){
                if (request.getParameter("total") != null) {
                    opcao = "cadastrar"; 
                } else {
                    opcao = "abrir";     
                }
            }

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
            request.setAttribute("mensagem", "ERRO: " + e.getMessage());
            encaminharPagina(request, response);
        }
    }

    protected void cadastrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
        Venda objVenda = new Venda(); 
        
        objVenda.setData(parseDate(request.getParameter("data")));
        objVenda.setTotal(parseDoubleSafely(request.getParameter("total")));
        objVenda.setDesconto(parseDoubleSafely(request.getParameter("desconto")));
        objVenda.setObservacao(request.getParameter("observacao"));
        
        // Seta os valores usando as propriedades completas das classes Java
        objVenda.getCliente().setCodigoCliente(Integer.valueOf(request.getParameter("vendaCliente")));
        objVenda.getFuncionario().setCodigoFuncionario(Integer.valueOf(request.getParameter("vendaFuncionario")));
        objVenda.getFormaPagamento().setCodigoFormaPagamento(Integer.valueOf(request.getParameter("vendaFormaPagamento")));

        objVendaDao.salvar(objVenda);

        request.setAttribute("mensagem", "Venda cadastrada com sucesso!");
        encaminharPagina(request, response);
    }

    protected void encaminharPagina(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("listaVenda", objVendaDao.buscarTodasVendas());
        request.setAttribute("listaCliente", objClienteDao.buscarTodos());
        request.setAttribute("listaFuncionario", objFuncionarioDao.buscarTodos());
        request.setAttribute("listaFormaPagamento", objFormaPagamentoDao.buscarTodos());

        RequestDispatcher rd = request.getRequestDispatcher("/CadastroVenda.jsp");
        rd.forward(request, response);
    }

    protected void enviarAlterar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
        request.setAttribute("codVenda", request.getParameter("codVenda"));
        request.setAttribute("data", request.getParameter("data"));
        request.setAttribute("total", request.getParameter("total"));
        request.setAttribute("desconto", request.getParameter("desconto"));
        request.setAttribute("observacao", request.getParameter("observacao"));
        request.setAttribute("vendaCliente", request.getParameter("vendaCliente"));
        request.setAttribute("vendaFuncionario", request.getParameter("vendaFuncionario"));
        request.setAttribute("vendaFormaPagamento", request.getParameter("vendaFormaPagamento"));
        request.setAttribute("opcao", "executarAlterar");
        request.setAttribute("mensagem","Edite os dados e clique no botão de salvar.");

        encaminharPagina(request, response);
    }

    protected void executarAlterar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Venda objVenda = new Venda();
        objVenda.setCodVenda(Integer.valueOf(request.getParameter("codVenda")));
        objVenda.setData(parseDate(request.getParameter("data")));
        objVenda.setTotal(parseDoubleSafely(request.getParameter("total")));
        objVenda.setDesconto(parseDoubleSafely(request.getParameter("desconto")));
        objVenda.setObservacao(request.getParameter("observacao"));
        objVenda.getCliente().setCodigoCliente(Integer.valueOf(request.getParameter("vendaCliente")));
        objVenda.getFuncionario().setCodigoFuncionario(Integer.valueOf(request.getParameter("vendaFuncionario")));
        objVenda.getFormaPagamento().setCodigoFormaPagamento(Integer.valueOf(request.getParameter("vendaFormaPagamento")));
        objVendaDao.alterar(objVenda);
        request.setAttribute("opcao", "cadastrar");
        
        
        request.setAttribute("mensagem", "Venda alterada com sucesso!");
        encaminharPagina(request, response);
    }

    protected void enviarExcluir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("codVenda", request.getParameter("codVenda"));
        request.setAttribute("data", request.getParameter("data"));
        request.setAttribute("total", request.getParameter("total"));
        request.setAttribute("desconto", request.getParameter("desconto"));
        request.setAttribute("observacao", request.getParameter("observacao"));
        request.setAttribute("vendaCliente", request.getParameter("vendaCliente"));
        request.setAttribute("vendaFuncionario", request.getParameter("vendaFuncionario"));
        request.setAttribute("vendaFormaPagamento", request.getParameter("vendaFormaPagamento"));
        request.setAttribute("opcao", "executarExcluir");
        request.setAttribute("mensagem", "Clique em salvar para confirmar exclusão");

        encaminharPagina(request, response);
    }

    protected void executarExcluir(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Venda objVenda = new Venda();
        objVenda.setCodVenda(Integer.valueOf(request.getParameter("codVenda")));
        objVendaDao.excluir(objVenda);

        request.setAttribute("opcao", "cadastrar");
        request.setAttribute("mensagem", "Venda excluída com sucesso");
        encaminharPagina(request, response);
    }

    protected void cancelar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        encaminharPagina(request, response); // Limpa e recarrega a página
    }

    private Date parseDate(String valor) {
        if (valor == null || valor.isEmpty()) {
            return null;
        }
        return Date.valueOf(valor);
    }
    
    private Double parseDoubleSafely(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return 0.0;
        }
        return Double.valueOf(valor.replace(",", "."));
    }
}