package br.edu.ifsuldeminas.supermercado.controlador;

import br.edu.ifsuldeminas.supermercado.relatorio.GeradorRelatoriosExcel;
import br.edu.ifsuldeminas.supermercado.relatorio.RelatorioDao;
import br.edu.ifsuldeminas.supermercado.servico.WebConstante;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(WebConstante.BASE_PATH + "/RelatorioExcelControlador")
public class RelatorioExcelControlador extends HttpServlet {

    private final RelatorioDao dao = new RelatorioDao();
    private final GeradorRelatoriosExcel excel = new GeradorRelatoriosExcel();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String tipo = req.getParameter("tipo");
        try {
            switch (tipo == null ? "" : tipo) {
                case "folha_rh":
                    excel.gerarFolhaRh(dao.buscarFolhaSalarial(), dao.buscarResumoPorCargo(), resp);
                    break;
                case "compras_fornecedor":
                    excel.gerarComprasFornecedor(dao.buscarComprasPorFornecedor(), resp);
                    break;
                case "curva_abc_clientes":
                    excel.gerarCurvaAbcClientes(dao.buscarCurvaAbcClientes(), resp);
                    break;
                case "margem_produtos":
                    excel.gerarMargemProdutos(dao.buscarMargemProdutos(), resp);
                    break;
                case "curva_abc_produtos":
                    excel.gerarCurvaAbcProdutos(dao.buscarCurvaAbcProdutos(), resp);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo de relatório Excel inválido.");
            }
        } catch (Exception e) {
            throw new ServletException("Erro ao gerar relatório Excel: " + e.getMessage(), e);
        }
    }
}