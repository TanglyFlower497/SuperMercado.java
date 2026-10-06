<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<nav>
    <ul>
        <li><a href="${pageContext.request.contextPath}/Home.jsp">HOME</a></li>
        <li><a href="${pageContext.request.contextPath}${URL_BASE}/ClienteControlador?opcao=cancelar">CLIENTE</a></li>
        <li><a href="${pageContext.request.contextPath}${URL_BASE}/FuncionarioControlador?opcao=cancelar">FUNCIONÁRIO</a></li>
        <li><a href="${pageContext.request.contextPath}${URL_BASE}/FornecedorControlador?opcao=cancelar">FORNECEDOR</a></li>
        <li><a href="${pageContext.request.contextPath}${URL_BASE}/CategoriaControlador?opcao=cancelar">CATEGORIA</a></li>
        <li><a href="${pageContext.request.contextPath}${URL_BASE}/FormaPagamentoControlador?opcao=cancelar">FORMA DE PAGAMENTO</a></li>
        <li><a href="${pageContext.request.contextPath}${URL_BASE}/ProdutoControlador?opcao=cancelar">PRODUTO</a></li>
        <li><a href="${pageContext.request.contextPath}${URL_BASE}/CompraControlador?opcao=cancelar">COMPRA</a></li>
        <li><a href="${pageContext.request.contextPath}${URL_BASE}/VendaControlador?opcao=cancelar">VENDA</a></li>
        <li><a href="${pageContext.request.contextPath}${URL_BASE}/ItensvendaControlador?opcao=cancelar">ITENSVENDA</a></li>
        <li><a href="${pageContext.request.contextPath}${URL_BASE}/ItenscompraControlador?opcao=cancelar">ITENSCOMPRA</a></li>
        <c:if test="${empty sessionScope.usuarioLogado}">
            <li><a href="${pageContext.request.contextPath}/login.jsp">LOGIN</a></li>
        </c:if>
        <c:if test="${not empty sessionScope.usuarioLogado}">
            <li><a href="${pageContext.request.contextPath}${URL_BASE}/LoginControlador">LOGOUT (<c:out value="${sessionScope.usuarioLogado.nomeUsuario}"/>)</a></li>
        </c:if>
        
    </ul>
</nav>