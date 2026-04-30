<%@page contentType="text/html" pageEncoding="UTF-8"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilo.css"/>

<nav>
    <ul>
        <li><a href="${pageContext.request.contextPath}/index.jsp">HOME</a></li>
        <li><a href="${pageContext.request.contextPath}${URL_BASE}/ClienteControlador?opcao=cancelar">CLIENTE</a></li>
       <!-- <li><a href="${pageContext.request.contextPath}${URL_BASE}/ProdutoControlador?opcao=cancelar">PRODUTO</a></li> -->
        <li><a href="${pageContext.request.contextPath}${URL_BASE}/FuncionarioControlador?opcao=cancelar">FUNCIONÁRIO</a></li>
        <li><a href="${pageContext.request.contextPath}${URL_BASE}/CategoriaControlador?opcao=cancelar">CATEGORIA</a></li>
        <li><a href="${pageContext.request.contextPath}${URL_BASE}/FornecedorControlador?opcao=cancelar">FORNECEDOR</a></li>
        <li><a href="${pageContext.request.contextPath}${URL_BASE}/FormaPagamentoControlador?opcao=cancelar">FORMA DE PAGAMENTO</a></li>
        <li><a href="${pageContext.request.contextPath}/login.jsp">LOGIN</a></li>
        <li><a href="${pageContext.request.contextPath}/logOut.jsp">LOGOUT</a></li>
    </ul>
</nav>