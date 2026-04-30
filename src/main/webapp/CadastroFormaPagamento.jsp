<%-- 
    Document   : CadastroFormaPagamento
    Created on : 29 de abr. de 2026, 22:28:14
    Author     : vcart
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Cadastro de Forma de Pagamento</title>
</head>

<%@include file="menu.jsp" %>

<body>

<h1>Forma de Pagamento</h1>

<form name="cadastro" method="get" action="${pageContext.request.contextPath}${URL_BASE}/FormaPagamentoControlador">
    
    <input type="hidden" name="opcao" value="${opcao}"/>
    <input type="hidden" name="codigoFormaPagamento" value="${codigoFormaPagamento}"/>

    <p><label>Descrição:</label> 
        <input type="text" name="descricaoFormaPagamento" value="${descricaoFormaPagamento}" size="40">
    </p>

    <input type="submit" value="Salvar" style="float:left; margin-right: 3px"/>
</form>

<form method="get" action="${pageContext.request.contextPath}${URL_BASE}/FormaPagamentoControlador">
    <input type="submit" value="Cancelar">
    <input type="hidden" name="opcao" value="cancelar">
</form>

<h2>${mensagem}</h2>

<table border="1">

<c:if test="${not empty listaFormaPagamento}">
<tr>
    <th>CÓDIGO</th>
    <th>DESCRIÇÃO</th>
    <th>ALTERAR</th>
    <th>EXCLUIR</th>
</tr>
</c:if>

<c:forEach var="fp" items="${listaFormaPagamento}">
<tr>
    <td>${fp.codigoFormaPagamento}</td>
    <td>${fp.descricaoFormaPagamento}</td>

    <td>
        <form method="get" action="${pageContext.request.contextPath}${URL_BASE}/FormaPagamentoControlador">
            <input type="hidden" name="opcao" value="enviarAlterar"/>
            <input type="hidden" name="codigoFormaPagamento" value="${fp.codigoFormaPagamento}"/>
            <input type="hidden" name="descricaoFormaPagamento" value="${fp.descricaoFormaPagamento}"/>
            <button type="submit">Alterar</button>
        </form>
    </td>

    <td>
        <form method="get" action="${pageContext.request.contextPath}${URL_BASE}/FormaPagamentoControlador">
            <input type="hidden" name="opcao" value="enviarExcluir"/>
            <input type="hidden" name="codigoFormaPagamento" value="${fp.codigoFormaPagamento}"/>
            <button type="submit">Excluir</button>
        </form>
    </td>
</tr>
</c:forEach>

</table>

</body>
</html>