<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="head.jsp" %>
    <title>Central de Relatórios Gerenciais - Atacadão</title>
</head>
<body>
    <%@ include file="menu.jsp" %>

    <div class="container-itens">
        <h1 class="titulo-pagina">Central de Relatórios Gerenciais</h1>

        <div class="caixas-container">
            <!-- ABORDAGEM 1: JASPERREPORTS (PDF) -->
            <div class="caixa">
                <h3>Abordagem 1: Relatórios Executivos (JasperReports - PDF)</h3>
                <form action="${pageContext.request.contextPath}${URL_BASE}/RelatorioJasperControlador" method="get" target="_blank">
                    <div class="form-group">
                        <label>Data Inicial (para relatórios por período):</label>
                        <input type="date" name="dataInicio" class="form-control">
                    </div>
                    <div class="form-group">
                        <label>Data Final:</label>
                        <input type="date" name="dataFim" class="form-control">
                    </div>
                    <div class="acao-bar">
                        <button type="submit" name="tipo" value="vendas_categoria" class="btn-salvar">01. Faturamento por Categoria (PDF + Gráfico)</button>
                        <button type="submit" name="tipo" value="ranking_vendedores" class="btn-salvar">02. Ranking de Vendedores (PDF + Gráfico)</button>
                        <button type="submit" name="tipo" value="estoque_validade" class="btn-salvar">03. Alerta de Estoque Crítico e Validade (PDF)</button>
                        <button type="submit" name="tipo" value="formas_pagamento" class="btn-salvar">04. Recebimentos por Forma de Pagamento (PDF)</button>
                        <button type="submit" name="tipo" value="auditoria" class="btn-salvar">05. Auditoria de Exclusões de Produtos (PDF)</button>
                    </div>
                </form>
            </div>

            <!-- ABORDAGEM 3: APACHE POI (EXCEL .XLSX) -->
            <div class="caixa">
                <h3>Abordagem 3: Planilhas Analíticas (Apache POI - Excel .xlsx)</h3>
                <form action="${pageContext.request.contextPath}${URL_BASE}/RelatorioExcelControlador" method="get">
                    <div class="acao-bar">
                        <button type="submit" name="tipo" value="folha_rh" class="btn-salvar">06. Folha Salarial e Custos por Cargo (.xlsx)</button>
                        <button type="submit" name="tipo" value="compras_fornecedor" class="btn-salvar">07. Compras e Concentração por Fornecedor (.xlsx)</button>
                        <button type="submit" name="tipo" value="curva_abc_clientes" class="btn-salvar">08. Curva ABC de Clientes e Fidelização (.xlsx)</button>
                        <button type="submit" name="tipo" value="margem_produtos" class="btn-salvar">09. Margem de Lucro Bruta e Precificação (.xlsx)</button>
                        <button type="submit" name="tipo" value="curva_abc_produtos" class="btn-salvar">10. Curva ABC de Produtos e Giro (.xlsx)</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>