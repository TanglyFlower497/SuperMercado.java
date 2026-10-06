<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
    <%@ include file="head.jsp" %>
    <meta name="referrer" content="no-referrer">
    <title>Nova senha - Atacado</title>
</head>
<body>

<div class="auth-wrapper">
    <div class="auth-card">
        <h1>Nova senha</h1>
        <p class="auth-sub">Escolha uma nova senha para a sua conta</p>

        <c:if test="${not empty erro}">
            <div class="auth-msg auth-erro"><c:out value="${erro}"/></div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}${URL_BASE}/RecuperarSenhaControlador">
            <input type="hidden" name="opcao" value="salvar">
            <input type="hidden" name="token" value="<c:out value="${token}"/>">
            <div class="form-group">
                <label for="senha">Nova senha (mínimo 6 caracteres)</label>
                <input type="password" id="senha" name="senha" required minlength="6" maxlength="72"
                       autocomplete="new-password" autofocus>
            </div>
            <div class="form-group">
                <label for="confirmaSenha">Confirmar nova senha</label>
                <input type="password" id="confirmaSenha" name="confirmaSenha" required minlength="6" maxlength="72"
                       autocomplete="new-password">
            </div>
            <button type="submit" class="btn-salvar">Salvar nova senha</button>
        </form>
    </div>
</div>

<footer class="footer">
    <p>© 2026 Atacadão - Todos os direitos reservados</p>
</footer>
</body>
</html>
