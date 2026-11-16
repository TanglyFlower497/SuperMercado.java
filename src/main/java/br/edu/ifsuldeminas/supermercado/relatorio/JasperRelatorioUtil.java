package br.edu.ifsuldeminas.supermercado.relatorio;

import br.edu.ifsuldeminas.supermercado.modelo.dao.ConnectionFactory;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class JasperRelatorioUtil {

    public static void exportarPdf(String caminhoJrxml, String nomeArquivoSaida,
            Map<String, Object> parametros, HttpServletResponse response) throws Exception {

        try (InputStream jrxmlStream = JasperRelatorioUtil.class.getResourceAsStream(caminhoJrxml);
             Connection con = ConnectionFactory.getInstance().getConnection()) {

            if (jrxmlStream == null) {
                throw new IllegalArgumentException("Arquivo de layout não encontrado: " + caminhoJrxml);
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, con);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline; filename=\"" + nomeArquivoSaida + "\"");

            try (ServletOutputStream out = response.getOutputStream()) {
                JasperExportManager.exportReportToPdfStream(jasperPrint, out);
                out.flush();
            }
        }
    }
}