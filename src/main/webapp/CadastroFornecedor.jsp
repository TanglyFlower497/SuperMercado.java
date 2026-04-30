<%-- 
    Document   : CadastroFornecedor
    Created on : 29 de abr. de 2026, 21:48:29
    Author     : vcart
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Cadastro de Fornecedores</title>
</head>

<%@include file="menu.jsp" %>

<body>

<h1>Fornecedor</h1>

<form name="cadastro" method="get" action="${pageContext.request.contextPath}${URL_BASE}/FornecedorControlador">
    
    <input type="hidden" name="opcao" value="${opcao}"/>
    <input type="hidden" name="codigoFornecedor" value="${codigoFornecedor}"/>

    <p><label>Nome:</label> <input type="text" name="nomeFornecedor" value="${nomeFornecedor}" size="40"></p>
    <p><label>CNPJ:</label> <input type="text" name="cnpjFornecedor" value="${cnpjFornecedor}" size="20"></p>
    <p><label>Telefone:</label> <input type="text" name="telefoneFornecedor" value="${telefoneFornecedor}" size="20"></p>
    <p><label>Email:</label> <input type="text" name="emailFornecedor" value="${emailFornecedor}" size="30"></p>

    <input type="submit" value="Salvar" style="float:left; margin-right: 3px"/>
</form>

<form method="get" action="${pageContext.request.contextPath}${URL_BASE}/FornecedorControlador">
    <input type="submit" value="Cancelar">
    <input type="hidden" name="opcao" value="cancelar">
</form>

<h2>${mensagem}</h2>

<table border="1">

<c:if test="${not empty listaFornecedor}">
<tr>
    <th>CÓDIGO</th>
    <th>NOME</th>
    <th>CNPJ</th>
    <th>TELEFONE</th>
    <th>EMAIL</th>
    <th>ALTERAR</th>
    <th>EXCLUIR</th>
</tr>
</c:if>

<c:forEach var="f" items="${listaFornecedor}">
<tr>
    <td>${f.codigoFornecedor}</td>
    <td>${f.nomeFornecedor}</td>
    <td>${f.cnpjFornecedor}</td>
    <td>${f.telefoneFornecedor}</td>
    <td>${f.emailFornecedor}</td>

    <td>
        <form method="get" action="${pageContext.request.contextPath}${URL_BASE}/FornecedorControlador">
            <input type="hidden" name="opcao" value="enviarAlterar"/>
            <input type="hidden" name="codigoFornecedor" value="${f.codigoFornecedor}"/>
            <input type="hidden" name="nomeFornecedor" value="${f.nomeFornecedor}"/>
            <input type="hidden" name="cnpjFornecedor" value="${f.cnpjFornecedor}"/>
            <input type="hidden" name="telefoneFornecedor" value="${f.telefoneFornecedor}"/>
            <input type="hidden" name="emailFornecedor" value="${f.emailFornecedor}"/>
            <button type="submit">Alterar</button>
        </form>
    </td>

    <td>
        <form method="get" action="${pageContext.request.contextPath}${URL_BASE}/FornecedorControlador">
            <input type="hidden" name="opcao" value="enviarExcluir"/>
            <input type="hidden" name="codigoFornecedor" value="${f.codigoFornecedor}"/>
            <button type="submit">Excluir</button>
        </form>
    </td>
</tr>
</c:forEach>

</table>

</body>
</html>
