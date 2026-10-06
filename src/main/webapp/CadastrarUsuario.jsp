<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
    <%@ include file="head.jsp" %>
    <title>Cadastrar Usuário - Atacado</title>
</head>
<body>

<div class="auth-wrapper">
    <div class="auth-card">
        <h1>Criar conta</h1>
        <p class="auth-sub">Preencha os dados para se cadastrar</p>

        <c:if test="${not empty erro}">
            <div class="auth-msg auth-erro"><c:out value="${erro}"/></div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}${URL_BASE}/UsuarioControlador">
            <div class="form-group">
                <label for="nomeUsuario">Nome de usuário</label>
                <input type="text" id="nomeUsuario" name="nomeUsuario" required minlength="3" maxlength="50"
                       autocomplete="username" autofocus value="<c:out value="${nomeUsuario}"/>">
            </div>
            <div class="form-group">
                <label for="email">E-mail</label>
                <input type="email" id="email" name="email" required maxlength="255"
                       autocomplete="email" value="<c:out value="${email}"/>">
            </div>
            <div class="form-group">
                <label for="senha">Senha (mínimo 6 caracteres)</label>
                <input type="password" id="senha" name="senha" required minlength="6" maxlength="72"
                       autocomplete="new-password">
            </div>
            <div class="form-group">
                <label for="confirmaSenha">Confirmar senha</label>
                <input type="password" id="confirmaSenha" name="confirmaSenha" required minlength="6" maxlength="72"
                       autocomplete="new-password">
            </div>
            <button type="submit" class="btn-salvar">Cadastrar</button>
        </form>

        <div class="auth-links">
            <a href="${pageContext.request.contextPath}/login.jsp">Já tenho conta</a>
        </div>
    </div>
</div>

<footer class="footer">
    <p>© 2026 Atacadão - Todos os direitos reservados</p>
</footer>
</body>
</html>
