package br.edu.ifsuldeminas.supermercado.relatorio;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class ExcelEstiloBase {

    protected CellStyle criarEstiloCabecalho(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        aplicarBordas(style);
        return style;
    }

    protected CellStyle criarEstiloMoeda(Workbook wb, boolean negrito) {
        CellStyle style = wb.createCellStyle();
        DataFormat fmt = wb.createDataFormat();
        style.setDataFormat(fmt.getFormat("R$ #,##0.00"));
        if (negrito) {
            Font f = wb.createFont();
            f.setBold(true);
            style.setFont(f);
            style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
        aplicarBordas(style);
        return style;
    }

    protected CellStyle criarEstiloPercentual(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setDataFormat(wb.createDataFormat().getFormat("0.00%"));
        aplicarBordas(style);
        return style;
    }

    protected CellStyle criarEstiloPadrao(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        aplicarBordas(style);
        return style;
    }

    private void aplicarBordas(CellStyle style) {
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
    }

    protected void finalizarLayout(Sheet... sheets) {
        for (Sheet s : sheets) {
            s.createFreezePane(0, 1);
            if (s.getRow(0) != null) {
                int cols = s.getRow(0).getPhysicalNumberOfCells();
                for (int i = 0; i < cols; i++) {
                    s.autoSizeColumn(i);
                }
            }
        }
    }

    protected void enviarResposta(Workbook wb, String fileName, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        try (ServletOutputStream out = resp.getOutputStream()) {
            wb.write(out);
            out.flush();
        }
        wb.close();
    }

    protected int asInt(Object val) {
        return val instanceof Number ? ((Number) val).intValue() : 0;
    }

    protected double asDouble(Object val) {
        return val instanceof Number ? ((Number) val).doubleValue() : 0.0;
    }
}