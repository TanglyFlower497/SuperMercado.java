<%-- 
    Document   : CategoriaControlador
    Created on : 29 de abr. de 2026, 21:41:30
    Author     : vcart
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Cadastro de Categorias</title>
</head>

<%@include file="menu.jsp" %>

<body>

<h1>Categoria</h1>

<form name="cadastro" method="get" action="${pageContext.request.contextPath}${URL_BASE}/CategoriaControlador">
    
    <input type="hidden" name="opcao" value="${opcao}"/>
    <input type="hidden" name="codigoCategoria" value="${codigoCategoria}"/>

    <p><label>Nome:</label> <input type="text" name="nomeCategoria" value="${nomeCategoria}" size="40"></p>
    <p><label>Descrição:</label> <input type="text" name="descricaoCategoria" value="${descricaoCategoria}" size="50"></p>

    <input type="submit" value="Salvar" name="Salvar" style="float:left; margin-right: 3px"/>
</form>

<form method="get" action="${pageContext.request.contextPath}${URL_BASE}/CategoriaControlador">
    <input type="submit" value="Cancelar">
    <input type="hidden" name="opcao" value="cancelar">
</form>

<h2>${mensagem}</h2>

<table border="1">

<c:if test="${not empty listaCategoria}">
<tr>
    <th>CÓDIGO</th>
    <th>NOME</th>
    <th>DESCRIÇÃO</th>
    <th>ALTERAR</th>
    <th>EXCLUIR</th>
</tr>
</c:if>

<c:forEach var="cat" items="${listaCategoria}">
<tr>
    <td>${cat.codigoCategoria}</td>
    <td>${cat.nomeCategoria}</td>
    <td>${cat.descricaoCategoria}</td>

    <td>
        <form method="get" action="${pageContext.request.contextPath}${URL_BASE}/CategoriaControlador">
            <input type="hidden" name="opcao" value="enviarAlterar"/>
            <input type="hidden" name="codigoCategoria" value="${cat.codigoCategoria}"/>
            <input type="hidden" name="nomeCategoria" value="${cat.nomeCategoria}"/>
            <input type="hidden" name="descricaoCategoria" value="${cat.descricaoCategoria}"/>
            <button type="submit">Alterar</button>
        </form>
    </td>

    <td>
        <form method="get" action="${pageContext.request.contextPath}${URL_BASE}/CategoriaControlador">
            <input type="hidden" name="opcao" value="enviarExcluir"/>
            <input type="hidden" name="codigoCategoria" value="${cat.codigoCategoria}"/>
            <button type="submit">Excluir</button>
        </form>
    </td>
</tr>
</c:forEach>

</table>

</body>
</html>