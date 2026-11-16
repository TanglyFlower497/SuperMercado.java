package br.edu.ifsuldeminas.supermercado.relatorio;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GeradorRelatoriosExcel extends ExcelEstiloBase {

    // RELATÓRIO 06: Folha Salarial e Resumo por Cargo (2 Abas)
    public void gerarFolhaRh(List<Map<String, Object>> funcionarios,
            List<Map<String, Object>> resumoCargos, HttpServletResponse resp) throws IOException {
        Workbook wb = new XSSFWorkbook();
        CellStyle header = criarEstiloCabecalho(wb);
        CellStyle moeda = criarEstiloMoeda(wb, false);
        CellStyle moedaTotal = criarEstiloMoeda(wb, true);
        CellStyle pct = criarEstiloPercentual(wb);
        CellStyle padrao = criarEstiloPadrao(wb);

        // Aba 1: Quadro Detalhado
        Sheet s1 = wb.createSheet("Quadro de Funcionários");
        String[] cols1 = {"ID", "Nome", "CPF", "Cargo", "Data Admissão", "Salário (R$)", "% da Folha"};
        Row r0 = s1.createRow(0);
        for (int i = 0; i < cols1.length; i++) {
            Cell c = r0.createCell(i);
            c.setCellValue(cols1[i]);
            c.setCellStyle(header);
        }

        int rowIdx = 1;
        int totalRowNum = funcionarios.size() + 2;
        for (Map<String, Object> f : funcionarios) {
            Row r = s1.createRow(rowIdx);
            r.createCell(0).setCellValue(asInt(f.get("codFuncionario")));
            r.createCell(1).setCellValue(String.valueOf(f.get("nome")));
            r.createCell(2).setCellValue(String.valueOf(f.get("cpf")));
            r.createCell(3).setCellValue(String.valueOf(f.get("cargo")));
            r.createCell(4).setCellValue(f.get("dataAdmissao") != null ? f.get("dataAdmissao").toString() : "-");
            for (int c = 0; c <= 4; c++) {
                r.getCell(c).setCellStyle(padrao);
            }

            Cell cSal = r.createCell(5);
            cSal.setCellValue(asDouble(f.get("salario")));
            cSal.setCellStyle(moeda);

            Cell cPct = r.createCell(6);
            cPct.setCellFormula("IFERROR(F" + (rowIdx + 1) + "/$F$" + totalRowNum + ",0)");
            cPct.setCellStyle(pct);
            rowIdx++;
        }

        Row rTot = s1.createRow(rowIdx);
        rTot.createCell(4).setCellValue("TOTAL FOLHA:");
        Cell cTot = rTot.createCell(5);
        cTot.setCellFormula(rowIdx > 1 ? "SUM(F2:F" + rowIdx + ")" : "0");
        cTot.setCellStyle(moedaTotal);

        // Aba 2: Resumo por Cargo
        Sheet s2 = wb.createSheet("Resumo por Cargo");
        String[] cols2 = {"Cargo", "Qtd. Colaboradores", "Total Salários (R$)", "Média Salarial (R$)"};
        Row h2 = s2.createRow(0);
        for (int i = 0; i < cols2.length; i++) {
            Cell c = h2.createCell(i);
            c.setCellValue(cols2[i]);
            c.setCellStyle(header);
        }
        int r2Idx = 1;
        for (Map<String, Object> cg : resumoCargos) {
            Row r = s2.createRow(r2Idx++);
            r.createCell(0).setCellValue(String.valueOf(cg.get("cargo")));
            r.getCell(0).setCellStyle(padrao);
            r.createCell(1).setCellValue(asInt(cg.get("qtdFuncionarios")));
            r.getCell(1).setCellStyle(padrao);
            Cell c1 = r.createCell(2);
            c1.setCellValue(asDouble(cg.get("totalSalarios")));
            c1.setCellStyle(moeda);
            Cell c2 = r.createCell(3);
            c2.setCellValue(asDouble(cg.get("mediaSalarial")));
            c2.setCellStyle(moeda);
        }

        finalizarLayout(s1, s2);
        enviarResposta(wb, "06_folha_salarial_rh.xlsx", resp);
    }

    // RELATÓRIO 07: Compras por Fornecedor
    public void gerarComprasFornecedor(List<Map<String, Object>> dados, HttpServletResponse resp) throws IOException {
        Workbook wb = new XSSFWorkbook();
        Sheet s = wb.createSheet("Compras por Fornecedor");
        CellStyle header = criarEstiloCabecalho(wb);
        CellStyle moeda = criarEstiloMoeda(wb, false);
        CellStyle moedaTotal = criarEstiloMoeda(wb, true);
        CellStyle padrao = criarEstiloPadrao(wb);

        String[] cols = {"Cód.", "Fornecedor", "CNPJ", "Cidade", "UF", "Qtd. Pedidos", "Total Comprado", "Ticket Médio", "Última Compra"};
        Row r0 = s.createRow(0);
        for (int i = 0; i < cols.length; i++) {
            Cell c = r0.createCell(i);
            c.setCellValue(cols[i]);
            c.setCellStyle(header);
        }

        int rowIdx = 1;
        for (Map<String, Object> d : dados) {
            Row r = s.createRow(rowIdx++);
            r.createCell(0).setCellValue(asInt(d.get("codFornecedor")));
            r.createCell(1).setCellValue(String.valueOf(d.get("nome")));
            r.createCell(2).setCellValue(String.valueOf(d.get("cnpj")));
            r.createCell(3).setCellValue(String.valueOf(d.get("cidade")));
            r.createCell(4).setCellValue(String.valueOf(d.get("uf")));
            r.createCell(5).setCellValue(asInt(d.get("qtdPedidos")));
            for (int c = 0; c <= 5; c++) {
                r.getCell(c).setCellStyle(padrao);
            }

            Cell cTot = r.createCell(6);
            cTot.setCellValue(asDouble(d.get("totalComprado")));
            cTot.setCellStyle(moeda);

            Cell cTkt = r.createCell(7);
            cTkt.setCellValue(asDouble(d.get("ticketMedio")));
            cTkt.setCellStyle(moeda);

            Cell cDt = r.createCell(8);
            cDt.setCellValue(d.get("ultimaCompra") != null ? d.get("ultimaCompra").toString() : "Sem compras");
            cDt.setCellStyle(padrao);
        }

        Row rTot = s.createRow(rowIdx);
        rTot.createCell(5).setCellValue("TOTAL GERAL:");
        Cell cSum = rTot.createCell(6);
        cSum.setCellFormula(rowIdx > 1 ? "SUM(G2:G" + rowIdx + ")" : "0");
        cSum.setCellStyle(moedaTotal);

        finalizarLayout(s);
        enviarResposta(wb, "07_compras_fornecedor.xlsx", resp);
    }

    // RELATÓRIO 08: Curva ABC de Clientes e Fidelização
    public void gerarCurvaAbcClientes(List<Map<String, Object>> dados, HttpServletResponse resp) throws IOException {
        Workbook wb = new XSSFWorkbook();
        Sheet s = wb.createSheet("Curva ABC Clientes");
        CellStyle header = criarEstiloCabecalho(wb);
        CellStyle moeda = criarEstiloMoeda(wb, false);
        CellStyle pct = criarEstiloPercentual(wb);
        CellStyle padrao = criarEstiloPadrao(wb);

        double faturamentoGlobal = dados.stream()
                .mapToDouble(m -> asDouble(m.get("totalLiquido"))).sum();

        String[] cols = {"Ranking", "Cliente", "CPF", "Telefone", "Qtd. Compras", "Total Gasto", "% Acumulado", "Classe ABC", "Dias sem Comprar"};
        Row r0 = s.createRow(0);
        for (int i = 0; i < cols.length; i++) {
            Cell c = r0.createCell(i);
            c.setCellValue(cols[i]);
            c.setCellStyle(header);
        }

        int rowIdx = 1;
        double acumulado = 0.0;
        for (Map<String, Object> d : dados) {
            double valor = asDouble(d.get("totalLiquido"));
            acumulado += valor;
            double percAcum = faturamentoGlobal > 0 ? (acumulado / faturamentoGlobal) : 0;
            String classe = percAcum <= 0.80 ? "Classe A (VIP)" : (percAcum <= 0.95 ? "Classe B" : "Classe C");

            Row r = s.createRow(rowIdx);
            r.createCell(0).setCellValue(rowIdx);
            r.createCell(1).setCellValue(String.valueOf(d.get("nome")));
            r.createCell(2).setCellValue(String.valueOf(d.get("cpf")));
            r.createCell(3).setCellValue(String.valueOf(d.get("telefone")));
            r.createCell(4).setCellValue(asInt(d.get("qtdCompras")));
            for (int c = 0; c <= 4; c++) {
                r.getCell(c).setCellStyle(padrao);
            }

            Cell cVal = r.createCell(5);
            cVal.setCellValue(valor);
            cVal.setCellStyle(moeda);

            Cell cAcum = r.createCell(6);
            cAcum.setCellValue(percAcum);
            cAcum.setCellStyle(pct);

            Cell cCls = r.createCell(7);
            cCls.setCellValue(classe);
            cCls.setCellStyle(padrao);

            Cell cDias = r.createCell(8);
            cDias.setCellValue(asInt(d.get("diasInativo")));
            cDias.setCellStyle(padrao);
            rowIdx++;
        }

        finalizarLayout(s);
        enviarResposta(wb, "08_curva_abc_clientes.xlsx", resp);
    }

    // RELATÓRIO 09: Margem de Lucro Bruta e Precificação
    public void gerarMargemProdutos(List<Map<String, Object>> dados, HttpServletResponse resp) throws IOException {
        Workbook wb = new XSSFWorkbook();
        Sheet s = wb.createSheet("Margem e Precificação");
        CellStyle header = criarEstiloCabecalho(wb);
        CellStyle moeda = criarEstiloMoeda(wb, false);
        CellStyle pct = criarEstiloPercentual(wb);
        CellStyle padrao = criarEstiloPadrao(wb);

        String[] cols = {"Cód.", "Cód. Barras", "Produto", "Categoria", "Preço Venda", "Custo", "Lucro Unit.", "Margem (%)", "Estoque", "Capital Custo"};
        Row r0 = s.createRow(0);
        for (int i = 0; i < cols.length; i++) {
            Cell c = r0.createCell(i);
            c.setCellValue(cols[i]);
            c.setCellStyle(header);
        }

        int rowIdx = 1;
        for (Map<String, Object> d : dados) {
            int excelRow = rowIdx + 1;
            Row r = s.createRow(rowIdx++);
            r.createCell(0).setCellValue(asInt(d.get("codProduto")));
            r.createCell(1).setCellValue(String.valueOf(d.get("codigoBarras")));
            r.createCell(2).setCellValue(String.valueOf(d.get("nome")));
            r.createCell(3).setCellValue(String.valueOf(d.get("categoria")));
            for (int c = 0; c <= 3; c++) {
                r.getCell(c).setCellStyle(padrao);
            }

            Cell cPreco = r.createCell(4);
            cPreco.setCellValue(asDouble(d.get("preco")));
            cPreco.setCellStyle(moeda);

            Cell cCusto = r.createCell(5);
            cCusto.setCellValue(asDouble(d.get("custo")));
            cCusto.setCellStyle(moeda);

            Cell cLucro = r.createCell(6);
            cLucro.setCellFormula("E" + excelRow + "-F" + excelRow);
            cLucro.setCellStyle(moeda);

            Cell cMargem = r.createCell(7);
            cMargem.setCellFormula("IFERROR((E" + excelRow + "-F" + excelRow + ")/E" + excelRow + ",0)");
            cMargem.setCellStyle(pct);

            Cell cEst = r.createCell(8);
            cEst.setCellValue(asInt(d.get("estoque")));
            cEst.setCellStyle(padrao);

            Cell cCap = r.createCell(9);
            cCap.setCellFormula("I" + excelRow + "*F" + excelRow);
            cCap.setCellStyle(moeda);
        }

        finalizarLayout(s);
        enviarResposta(wb, "09_margem_lucro_produtos.xlsx", resp);
    }

    // RELATÓRIO 10: Curva ABC de Produtos e Giro de Estoque
    public void gerarCurvaAbcProdutos(List<Map<String, Object>> dados, HttpServletResponse resp) throws IOException {
        Workbook wb = new XSSFWorkbook();
        Sheet s = wb.createSheet("Curva ABC Produtos");
        CellStyle header = criarEstiloCabecalho(wb);
        CellStyle moeda = criarEstiloMoeda(wb, false);
        CellStyle pct = criarEstiloPercentual(wb);
        CellStyle padrao = criarEstiloPadrao(wb);

        double receitaTotal = dados.stream()
                .mapToDouble(m -> asDouble(m.get("receitaGerada"))).sum();

        String[] cols = {"Ranking", "Cód. Barras", "Produto", "Categoria", "Qtd. Vendida", "Receita Gerada", "% Acumulado", "Classe ABC", "Estoque Atual"};
        Row r0 = s.createRow(0);
        for (int i = 0; i < cols.length; i++) {
            Cell c = r0.createCell(i);
            c.setCellValue(cols[i]);
            c.setCellStyle(header);
        }

        int rowIdx = 1;
        double acumulado = 0.0;
        for (Map<String, Object> d : dados) {
            double rec = asDouble(d.get("receitaGerada"));
            acumulado += rec;
            double percAcum = receitaTotal > 0 ? (acumulado / receitaTotal) : 1.0;
            String classe = rec == 0 ? "Sem Giro" : (percAcum <= 0.80 ? "Classe A" : (percAcum <= 0.95 ? "Classe B" : "Classe C"));

            Row r = s.createRow(rowIdx);
            r.createCell(0).setCellValue(rowIdx);
            r.createCell(1).setCellValue(String.valueOf(d.get("codigoBarras")));
            r.createCell(2).setCellValue(String.valueOf(d.get("nome")));
            r.createCell(3).setCellValue(String.valueOf(d.get("categoria")));
            r.createCell(4).setCellValue(asInt(d.get("qtdVendida")));
            for (int c = 0; c <= 4; c++) {
                r.getCell(c).setCellStyle(padrao);
            }

            Cell cRec = r.createCell(5);
            cRec.setCellValue(rec);
            cRec.setCellStyle(moeda);

            Cell cPct = r.createCell(6);
            cPct.setCellValue(percAcum);
            cPct.setCellStyle(pct);

            Cell cCls = r.createCell(7);
            cCls.setCellValue(classe);
            cCls.setCellStyle(padrao);

            Cell cEst = r.createCell(8);
            cEst.setCellValue(asInt(d.get("estoque")));
            cEst.setCellStyle(padrao);
            rowIdx++;
        }

        finalizarLayout(s);
        enviarResposta(wb, "10_curva_abc_produtos.xlsx", resp);
    }
}