<%-- 
    Document   : CadastroProduto
    Created on : 01 de jul. de 2026
    Author     : vcart
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
    <%@ include file="head.jsp" %>
    <title>Cadastro de Produtos</title>
</head>
    <body>
        <%@include file="menu.jsp" %>
        <h1>Produto</h1>
        <form name="cadastro" method="get" action="${pageContext.request.contextPath}${URL_BASE}/ProdutoControlador">
            
            <input type="hidden" name="opcao" value="${opcao}"/>
            <input type="hidden" name="codigoProduto" value="${codigoProduto}"/>
            <p><label>Nome:</label> <input type="text" name="nomeProduto" required="" value="${nomeProduto}" size="40"></p>
            <p><label>Código de Barras:</label> <input type="text" name="codigoBarrasProduto" required="" value="${codigoBarrasProduto}" size="30"></p>
            <p><label>Preço:</label> <input type="number" step="0.01" name="precoProduto" required="" value="${precoProduto}" size="10"></p>
            <p><label>Custo:</label> <input type="number" step="0.01" name="custoProduto" required="" value="${custoProduto}" size="10"></p>
            <p><label>Estoque:</label> <input type="number" name="estoqueProduto" required="" value="${estoqueProduto}" size="10"></p>
            <p><label>Estoque Mínimo:</label> <input type="number" name="estoqueMinimoProduto" required=""  value="${estoqueMinimoProduto}" size="10"></p>
            <p><label>Validade:</label> <input type="date" name="validadeProduto" required="" value="${validadeProduto}"></p>
            <p><label>Marca:</label> <input type="text" name="marcaProduto" required="" value="${marcaProduto}" size="30"></p>

            <p><label>Categoria:</label>
                <select name="produtoCategoria" required>
                    <option value="">Selecione...</option>
                    <c:forEach var="categoria" items="${listaCategoria}">
                        <option
                            value="${categoria.codigoCategoria}"
                            ${categoria.codigoCategoria == produtoCategoria ? 'selected' : ''}>
                            ${categoria.nomeCategoria}
                        </option>
                    </c:forEach>
                </select>
            </p>

            <input type="submit" value="Salvar" name="Salvar" style="float:left; margin-right: 3px"/>
        </form>
        <form method="get" action="${pageContext.request.contextPath}${URL_BASE}/ProdutoControlador">
            <input type="submit" value="Cancelar" name="btnCancelar">
            <input type="hidden" name="opcao" value="cancelar">
        </form>
        <h2>${mensagem}</h2>

        <table border="1">
            <c:if test="${not empty listaProduto}">
                <tr>
                    <th>CÓDIGO</th>
                    <th>NOME</th>
                    <th>COD. BARRAS</th>
                    <th>PREÇO</th>
                    <th>CUSTO</th>
                    <th>ESTOQUE</th>
                    <th>EST. MÍNIMO</th>
                    <th>VALIDADE</th>
                    <th>MARCA</th>
                    <th>CATEGORIA</th>
                    <th>ALTERAR</th>
                    <th>EXCLUIR</th>
                </tr>
            </c:if>
            <c:forEach var="p" items="${listaProduto}">
                <tr>
                    <td>${p.codigoProduto}</td>
                    <td>${p.nomeProduto}</td>
                    <td>${p.codigoBarrasProduto}</td>
                    <td>${p.precoProduto}</td>
                    <td>${p.custoProduto}</td>
                    <td>${p.estoqueProduto}</td>
                    <td>${p.estoqueMinimoProduto}</td>
                    <td>${p.validadeProduto}</td>
                    <td>${p.marcaProduto}</td>
                    <td>${p.categoria.nomeCategoria}</td>
                    <td>
                        <form method="get" action="${pageContext.request.contextPath}${URL_BASE}/ProdutoControlador">
                            <input type="hidden" name="opcao" value="enviarAlterar"/>
                            <input type="hidden" name="codigoProduto" value="${p.codigoProduto}"/>
                            <input type="hidden" name="nomeProduto" value="${p.nomeProduto}"/>
                            <input type="hidden" name="codigoBarrasProduto" value="${p.codigoBarrasProduto}"/>
                            <input type="hidden" name="precoProduto" value="${p.precoProduto}"/>
                            <input type="hidden" name="custoProduto" value="${p.custoProduto}"/>
                            <input type="hidden" name="estoqueProduto" value="${p.estoqueProduto}"/>
                            <input type="hidden" name="estoqueMinimoProduto" value="${p.estoqueMinimoProduto}"/>
                            <input type="hidden" name="validadeProduto" value="${p.validadeProduto}"/>
                            <input type="hidden" name="marcaProduto" value="${p.marcaProduto}"/>
                            <input type="hidden" name="produtoCategoria" value="${p.categoria.codigoCategoria}"/>
                            <button type="submit">Alterar</button>
                        </form>
                    </td>
                    <td>
                        <form method="get" action="${pageContext.request.contextPath}${URL_BASE}/ProdutoControlador">
                            <input type="hidden" name="opcao" value="enviarExcluir"/>
                            <input type="hidden" name="codigoProduto" value="${p.codigoProduto}"/>
                            <input type="hidden" name="nomeProduto" value="${p.nomeProduto}"/>
                            <input type="hidden" name="codigoBarrasProduto" value="${p.codigoBarrasProduto}"/>
                            <input type="hidden" name="precoProduto" value="${p.precoProduto}"/>
                            <input type="hidden" name="custoProduto" value="${p.custoProduto}"/>
                            <input type="hidden" name="estoqueProduto" value="${p.estoqueProduto}"/>
                            <input type="hidden" name="estoqueMinimoProduto" value="${p.estoqueMinimoProduto}"/>
                            <input type="hidden" name="validadeProduto" value="${p.validadeProduto}"/>
                            <input type="hidden" name="marcaProduto" value="${p.marcaProduto}"/>
                            <input type="hidden" name="produtoCategoria" value="${p.categoria.codigoCategoria}"/>
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
