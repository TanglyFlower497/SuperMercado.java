-<%-- 
    Document   : Home
    Created on : 2 de jul de 2026, 12:11:06
    Author     : 12421698650
--%>

<%@page contentType="text/html" pageEncoding="Latin1"%>

<!DOCTYPE html>

<html> 
    <head>
    <%@ include file="head.jsp" %>
    <title>Sistema - Atacadão</title>
</head>
    <body>

<!-- Cabeçalho -->
<header>
    <h1>Atacadão</h1>
    <p>Sistema de Gerenciamento de Supermercado</p>
</header>

<!-- Menu -->
<nav >
    <ul>
        <li><a href="${pageContext.request.contextPath}${URL_BASE}/ClienteControlador?opcao=abrir">Cliente</a></li>
        <li><a href="${pageContext.request.contextPath}${URL_BASE}/FuncionarioControlador?opcao=abrir">Funcionario</a></li> 
        <li><a href="${pageContext.request.contextPath}${URL_BASE}/FornecedorControlador?opcao=abrir">Fornecedor</a></li>
        <li><a href="${pageContext.request.contextPath}${URL_BASE}/CategoriaControlador?opcao=abrir">Categoria</a></li>
        <li><a href="${pageContext.request.contextPath}${URL_BASE}/FormaPagamentoControlador?opcao=abrir">Forma de Pagamento</a></li>
        <li><a href="${pageContext.request.contextPath}${URL_BASE}/ProdutoControlador?opcao=abrir">Produto</a></li>
        <li><a href="${pageContext.request.contextPath}${URL_BASE}/CompraControlador?opcao=abrir">Compra</a></li>
        <li><a href="${pageContext.request.contextPath}${URL_BASE}/VendaControlador?opcao=cancelar">Venda</a></li>
        <li><a href="${pageContext.request.contextPath}${URL_BASE}/ItensvendaControlador?opcao=cancelar">Itens Venda</a></li>
        <li><a href="${pageContext.request.contextPath}${URL_BASE}/ItenscompraControlador?opcao=cancelar">Itens Compra</a></li>
        <li><a href="${pageContext.request.contextPath}${URL_BASE}/LoginControlador">Logout (${sessionScope.usuarioLogado.nomeUsuario})</a></li>
        
        
     
        
     
        
       
    </ul>
</nav>


<main>
    <h2>Bem-vindo ao Sistema</h2>
    <p>Utilize o menu acima para gerenciar as informações do supermercado.</p>

    <h3>Informações do Supermercado</h3>
    <p><strong>Nome:</strong> Atacadão</p>
    <p><strong>Endereço:</strong> Rua das Palmeiras, 123 - Centro, Paraguaçu - MG</p>

    <h3>Desenvolvedores</h3>
    <p>Higor Miranda</p>
    <p>Augusto Lemos</p>
</main>


<footer>
    <p>© 2026 Atacadão - Todos os direitos reservados</p>
</footer>

</body> </html>