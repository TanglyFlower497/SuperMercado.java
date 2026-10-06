<%-- 
    Document   : CadastroCompra
    Created on : 1 de jul. de 2026, 19:28:43
    Author     : Higor
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
    <%@ include file="head.jsp" %>
    <title>Cadastro de Compras</title>
</head>
    <body>
        <%@include file="menu.jsp" %>
        <h1>Compra</h1>
        <form name="cadastro" method="get" action="${pageContext.request.contextPath}${URL_BASE}/CompraControlador">
            <input type="hidden" name="opcao" value="${opcao}"/>
            <input type="hidden" name="codCompra" value="${codCompra}"/>
            <p><label>Data:</label> <input type="date" name="data" required="" value="${data}" size="40" /></p>
            <p><label>Total:</label> <input type="number" step="0.01" required="" name="total" value="${total}" size="40" required/></p>
            <p><label>Observação:</label> <input type="text" name="observacao" value="${observacao}" size="40" /></p>
            <p><label>Fornecedor:</label>
                <select name="compraFornecedor" required>
                    <option value="">Selecione...</option>
                    <c:forEach var="fornecedor" items="${listaFornecedor}">
                        <option
                            value="${fornecedor.codigoFornecedor}"
                            ${fornecedor.codigoFornecedor == compraFornecedor ? 'selected' : ''}>
                            ${fornecedor.nomeFornecedor}
                        </option>
                    </c:forEach>
                </select>
            </p>
            <input type="submit" value="Salvar" name="Salvar" style="float:left; margin-right:3px"/>
        </form>
        <form method="get" action="${pageContext.request.contextPath}${URL_BASE}/CompraControlador">
            <input type="submit" value="Cancelar" name="btnCancelar">
            <input type="hidden" name="opcao" value="cancelar">
        </form>
        <h2>${mensagem}</h2>

        <table border="1">
            <c:if test="${not empty listaCompra}">
                <tr>
                    <th>Código</th>
                    <th>Data</th>
                    <th>Total</th>
                    <th>Fornecedor</th>
                    <th>Observação</th>
                    <th>Alterar</th>
                    <th>Excluir</th>
                </tr>
            </c:if>

            <c:forEach var="compra" items="${listaCompra}">
                <tr>
                    <td>${compra.codCompra}</td>
                    <td>${compra.data}</td>
                    <td>${compra.total}</td>
                    <td>${compra.fornecedor.nomeFornecedor}</td>
                    <td>${compra.observacao}</td>
                    <td>
                        <form method="get" action="${pageContext.request.contextPath}${URL_BASE}/CompraControlador">
                            <input type="hidden" name="opcao" value="enviarAlterar"/>
                            <input type="hidden" name="codCompra" value="${compra.codCompra}"/>
                            <input type="hidden" name="data" value="${compra.data}"/>
                            <input type="hidden" name="compraFornecedor" value="${compra.fornecedor.codigoFornecedor}"/>
                            <input type="hidden" name="total" value="${compra.total}"/>
                            <input type="hidden" name="observacao" value="${compra.observacao}"/>
                            <button type="submit">Alterar</button>
                        </form>
                    </td>
                    <td>
                        <form method="get" action="${pageContext.request.contextPath}${URL_BASE}/CompraControlador">
                            <input type="hidden" name="opcao" value="enviarExcluir"/>
                            <input type="hidden" name="codCompra" value="${compra.codCompra}"/>
                            <input type="hidden" name="data" value="${compra.data}"/>
                            <input type="hidden" name="compraFornecedor" value="${compra.fornecedor.codigoFornecedor}"/>
                            <input type="hidden" name="total" value="${compra.total}"/>
                            <input type="hidden" name="observacao" value="${compra.observacao}"/>
                            <button type="submit">Excluir</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <footer class="footer">
    <p>© 2026 Atacadão - Todos os direitos reservados</p>
</footer>

    </body>
</html>
