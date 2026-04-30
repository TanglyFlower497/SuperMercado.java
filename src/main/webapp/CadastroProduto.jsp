<%-- 
    Document   : CadastroProduto
    Created on : 29 de abr. de 2026, 21:33:46
    Author     : vcart
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Cadastro de Produtos</title>
</head>

<%@include file="menu.jsp" %>

<body>

<h1>Produto</h1>

<form name="cadastro" method="get" action="${pageContext.request.contextPath}${URL_BASE}/ProdutoControlador">
    
    <input type="hidden" name="opcao" value="${opcao}"/>
    <input type="hidden" name="codigoProduto" value="${codigoProduto}"/>

    <p><label>Nome:</label> <input type="text" name="nomeProduto" value="${nomeProduto}" size="40"></p>
    <p><label>Preço:</label> <input type="text" name="precoProduto" value="${precoProduto}" size="15"></p>
    <p><label>Estoque:</label> <input type="text" name="estoqueProduto" value="${estoqueProduto}" size="10"></p>
    <!-- ---------CATEGORIA------------>
      <p>
      <label>Categoria:</label>
      <!-- não estou conseguindo passar os valores da categoria, nao aparece para selecioanar -->
      <select name="codigoCategoria">
        <option value="">Selecione</option>

        <c:forEach var="cat" items="${listaCategoria}">
            <option value="${cat.codigoCategoria}"
                <c:if test="${cat.codigoCategoria == codigoCategoria}">selected</c:if>>
                ${cat.nomeCategoria}
            </option>
        </c:forEach>
      </select>
      </p>

    <input type="submit" value="Salvar" name="Salvar" style="float:left; margin-right: 3px"/>
</form>

<form method="get" action="${pageContext.request.contextPath}${URL_BASE}/ProdutoControlador">
    <input type="submit" value="Cancelar">
    <input type="hidden" name="opcao" value="cancelar">
</form>

<h2>${mensagem}</h2>

<table border="1">

<c:if test="${not empty listaProduto}">
<tr>
    <th>CÓDIGO</th>
    <th>NOME</th>
    <th>PREÇO</th>
    <th>ESTOQUE</th>
    <th>ALTERAR</th>
    <th>EXCLUIR</th>
</tr>
</c:if>

<c:forEach var="p" items="${listaProduto}">
<tr>
    <td>${p.codigoProduto}</td>
    <td>${p.nomeProduto}</td>
    <td>${p.precoProduto}</td>
    <td>${p.estoqueProduto}</td>

    <td>
        <form method="get" action="${pageContext.request.contextPath}${URL_BASE}/ProdutoControlador">
            <input type="hidden" name="opcao" value="enviarAlterar"/>
            <input type="hidden" name="codigoProduto" value="${p.codigoProduto}"/>
            <input type="hidden" name="nomeProduto" value="${p.nomeProduto}"/>
            <input type="hidden" name="precoProduto" value="${p.precoProduto}"/>
            <input type="hidden" name="estoqueProduto" value="${p.estoqueProduto}"/>
            <button type="submit">Alterar</button>
        </form>
    </td>

    <td>
        <form method="get" action="${pageContext.request.contextPath}${URL_BASE}/ProdutoControlador">
            <input type="hidden" name="opcao" value="enviarExcluir"/>
            <input type="hidden" name="codigoProduto" value="${p.codigoProduto}"/>
            <button type="submit">Excluir</button>
        </form>
    </td>
</tr>
</c:forEach>

</table>

</body>
</html>
