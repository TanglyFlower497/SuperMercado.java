package br.edu.ifsuldeminas.supermercado.controlador;

import br.edu.ifsuldeminas.supermercado.entidade.Usuario;
import br.edu.ifsuldeminas.supermercado.relatorio.JasperRelatorioUtil;
import br.edu.ifsuldeminas.supermercado.servico.AuthFilter;
import br.edu.ifsuldeminas.supermercado.servico.WebConstante;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@WebServlet(WebConstante.BASE_PATH + "/RelatorioJasperControlador")
public class RelatorioJasperControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String tipo = req.getParameter("tipo");
        String dtIniStr = req.getParameter("dataInicio");
        String dtFimStr = req.getParameter("dataFim");

        LocalDate hoje = LocalDate.now();
        Date dataInicio = (dtIniStr != null && !dtIniStr.isBlank())
                ? Date.valueOf(dtIniStr) : Date.valueOf(hoje.minusDays(30));
        Date dataFim = (dtFimStr != null && !dtFimStr.isBlank())
                ? Date.valueOf(dtFimStr) : Date.valueOf(hoje);

        Usuario logado = (Usuario) req.getSession().getAttribute(AuthFilter.ATRIBUTO_SESSAO);
        Map<String, Object> params = new HashMap<>();
        params.put("DATA_INICIO", dataInicio);
        params.put("DATA_FIM", dataFim);
        params.put("USUARIO_EMISSOR", logado != null ? logado.getNomeUsuario() : "Sistema");

        try {
            switch (tipo == null ? "" : tipo) {
                case "vendas_categoria":
                    JasperRelatorioUtil.exportarPdf("/relatorios/relatorio_vendas_categoria.jrxml",
                            "01_faturamento_categoria.pdf", params, resp);
                    break;
                case "ranking_vendedores":
                    JasperRelatorioUtil.exportarPdf("/relatorios/relatorio_ranking_vendedores.jrxml",
                            "02_ranking_vendedores.pdf", params, resp);
                    break;
                case "estoque_validade":
                    JasperRelatorioUtil.exportarPdf("/relatorios/relatorio_estoque_validade.jrxml",
                            "03_alerta_estoque_validade.pdf", params, resp);
                    break;
                case "formas_pagamento":
                    JasperRelatorioUtil.exportarPdf("/relatorios/relatorio_formas_pagamento.jrxml",
                            "04_recebimentos_pagamento.pdf", params, resp);
                    break;
                case "auditoria":
                    JasperRelatorioUtil.exportarPdf("/relatorios/relatorio_auditoria.jrxml",
                            "05_auditoria_sistema.pdf", params, resp);
                    break;
                default:
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo de relatório PDF inválido.");
            }
        } catch (Exception e) {
            throw new ServletException("Erro ao gerar relatório JasperReports: " + e.getMessage(), e);
        }
    }
}