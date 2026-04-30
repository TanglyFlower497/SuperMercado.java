<%-- 
    Document   : CadastroCliente
    Created on : 29 de abr. de 2026, 21:26:34
    Author     : vcart
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Cadastro de Clientes</title>
</head>

<%@include file="menu.jsp" %>

<body>

<h1>Cliente</h1>

<form name="cadastro" method="get" action="${pageContext.request.contextPath}${URL_BASE}/ClienteControlador">
    
    <input type="hidden" name="opcao" value="${opcao}"/>
    <input type="hidden" name="codigoCliente" value="${codigoCliente}"/>

    <p><label>Nome:</label> <input type="text" name="nomeCliente" value="${nomeCliente}" size="40"></p>
    <p><label>CPF:</label> <input type="text" name="cpfCliente" value="${cpfCliente}" size="20"></p>
    <p><label>Telefone:</label> <input type="text" name="telefoneCliente" value="${telefoneCliente}" size="20"></p>
    <p><label>Email:</label> <input type="text" name="emailCliente" value="${emailCliente}" size="30"></p>

    <input type="submit" value="Salvar" name="Salvar" style="float:left; margin-right: 3px"/>
</form>

<form method="get" action="${pageContext.request.contextPath}${URL_BASE}/ClienteControlador">
    <input type="submit" value="Cancelar" name="btnCancelar">
    <input type="hidden" name="opcao" value="cancelar">
</form>

<h2>${mensagem}</h2>

<table border="1">

<c:if test="${not empty listaCliente}">
<tr>
    <th>CÓDIGO</th>
    <th>NOME</th>
    <th>CPF</th>
    <th>TELEFONE</th>
    <th>EMAIL</th>
    <th>ALTERAR</th>
    <th>EXCLUIR</th>
</tr>
</c:if>

<c:forEach var="c" items="${listaCliente}">
<tr>
    <td>${c.codigoCliente}</td>
    <td>${c.nomeCliente}</td>
    <td>${c.cpfCliente}</td>
    <td>${c.telefoneCliente}</td>
    <td>${c.emailCliente}</td>

    <td>
        <form method="get" action="${pageContext.request.contextPath}${URL_BASE}/ClienteControlador">
            <input type="hidden" name="opcao" value="enviarAlterar"/>
            <input type="hidden" name="codigoCliente" value="${c.codigoCliente}"/>
            <input type="hidden" name="nomeCliente" value="${c.nomeCliente}"/>
            <input type="hidden" name="cpfCliente" value="${c.cpfCliente}"/>
            <input type="hidden" name="telefoneCliente" value="${c.telefoneCliente}"/>
            <input type="hidden" name="emailCliente" value="${c.emailCliente}"/>
            <button type="submit">Alterar</button>
        </form>
    </td>

    <td>
        <form method="get" action="${pageContext.request.contextPath}${URL_BASE}/ClienteControlador">
            <input type="hidden" name="opcao" value="enviarExcluir"/>
            <input type="hidden" name="codigoCliente" value="${c.codigoCliente}"/>
            <button type="submit">Excluir</button>
        </form>
    </td>
</tr>
</c:forEach>

</table>

</body>
</html>
