<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
    <%@ include file="head.jsp" %>
    <title>Cadastro de Vendas</title>
</head>
    <body>
        <%@include file="menu.jsp" %>
        <h1>Venda</h1>
        <form name="cadastro" method="get" action="${pageContext.request.contextPath}${URL_BASE}/VendaControlador">
            <input type="hidden" name="opcao" value="${opcao}"/>
            <input type="hidden" name="codVenda" value="${codVenda}"/>
            <p><label>Data:</label> <input type="date" name="data" required="" value="${data}" size="40" /></p>
            <p><label>Total:</label> <input type="number" step="0.01" required="" name="total" value="${total}" size="40" required/></p>
            <p><label>Desconto:</label> <input type="number" step="0.01" required="" name="desconto" value="${desconto}" size="40" required/></p>
            <p><label>Observação:</label> <input type="text" name="observacao" value="${observacao}" size="40" /></p>

            <p><label>Cliente:</label>
                <select name="vendaCliente" required>
                    <option value="">Selecione...</option>
                    <c:forEach var="cliente" items="${listaCliente}">
                        <option
                            value="${cliente.codigoCliente}"
                            ${cliente.codigoCliente == vendaCliente ? 'selected' : ''}>
                            ${cliente.nomeCliente}
                        </option>
                    </c:forEach>
                </select>
            </p>

            <p><label>Funcionário:</label>
                <select name="vendaFuncionario" required>
                    <option value="">Selecione...</option>
                    <c:forEach var="funcionario" items="${listaFuncionario}">
                        <option
                            value="${funcionario.codigoFuncionario}"
                            ${funcionario.codigoFuncionario == vendaFuncionario ? 'selected' : ''}>
                            ${funcionario.nomeFuncionario}
                        </option>
                    </c:forEach>
                </select>
            </p>

            <p><label>Forma de Pagamento:</label>
                <select name="vendaFormaPagamento" required>
                    <option value="">Selecione...</option>
                    <c:forEach var="forma" items="${listaFormaPagamento}">
                        <option
                            value="${forma.codigoFormaPagamento}"
                            ${forma.codigoFormaPagamento == vendaFormaPagamento ? 'selected' : ''}>
                            ${forma.descricaoFormaPagamento}
                        </option>
                    </c:forEach>
                </select>
            </p>

            <input type="submit" value="Salvar" name="Salvar" style="float:left; margin-right:3px"/>
        </form>
        
        <form method="get" action="${pageContext.request.contextPath}${URL_BASE}/VendaControlador">
            <input type="submit" value="Cancelar" name="btnCancelar">
            <input type="hidden" name="opcao" value="cancelar">
        </form>
        
        <h2>${mensagem}</h2>

        <table border="1">
            <c:if test="${not empty listaVenda}">
                <tr>
                    <th>Código</th>
                    <th>Data</th>
                    <th>Total</th>
                    <th>Desconto</th>
                    <th>Cliente</th>
                    <th>Funcionário</th>
                    <th>Forma de Pag.</th>
                    <th>Observação</th>
                    <th>Alterar</th>
                    <th>Excluir</th>
                </tr>
            </c:if>

            <c:forEach var="venda" items="${listaVenda}">
                <tr>
                    <td>${venda.codVenda}</td>
                    <td>${venda.data}</td>
                    <td>${venda.total}</td>
                    <td>${venda.desconto}</td>
                    <td>${venda.cliente.nomeCliente}</td>
                    <td>${venda.funcionario.nomeFuncionario}</td>
                    <td>${venda.formaPagamento.descricaoFormaPagamento}</td>
                    <td>${venda.observacao}</td>
                    <td>
                        <form method="get" action="${pageContext.request.contextPath}${URL_BASE}/VendaControlador">
                            <input type="hidden" name="opcao" value="enviarAlterar"/>
                            <input type="hidden" name="codVenda" value="${venda.codVenda}"/>
                            <input type="hidden" name="data" value="${venda.data}"/>
                            <input type="hidden" name="total" value="${venda.total}"/>
                            <input type="hidden" name="desconto" value="${venda.desconto}"/>
                            <input type="hidden" name="vendaCliente" value="${venda.cliente.codigoCliente}"/>
                            <input type="hidden" name="vendaFuncionario" value="${venda.funcionario.codigoFuncionario}"/>
                            <input type="hidden" name="vendaFormaPagamento" value="${venda.formaPagamento.codigoFormaPagamento}"/>
                            <input type="hidden" name="observacao" value="${venda.observacao}"/>
                            <button type="submit">Alterar</button>
                        </form>
                    </td>
                    <td>
                        <form method="get" action="${pageContext.request.contextPath}${URL_BASE}/VendaControlador">
                            <input type="hidden" name="opcao" value="enviarExcluir"/>
                            <input type="hidden" name="codVenda" value="${venda.codVenda}"/>
                            <input type="hidden" name="data" value="${venda.data}"/>
                            <input type="hidden" name="total" value="${venda.total}"/>
                            <input type="hidden" name="desconto" value="${venda.desconto}"/>
                            <input type="hidden" name="vendaCliente" value="${venda.cliente.codigoCliente}"/>
                            <input type="hidden" name="vendaFuncionario" value="${venda.funcionario.codigoFuncionario}"/>
                            <input type="hidden" name="vendaFormaPagamento" value="${venda.formaPagamento.codigoFormaPagamento}"/>
                            <input type="hidden" name="observacao" value="${venda.observacao}"/>
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