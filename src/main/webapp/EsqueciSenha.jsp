<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
    <%@ include file="head.jsp" %>
    <title>Esqueci minha senha - Atacado</title>
</head>
<body>

<div class="auth-wrapper">
    <div class="auth-card">
        <h1>Esqueci minha senha</h1>
        <p class="auth-sub">Informe o e-mail da sua conta e enviaremos um link para criar uma nova senha</p>

        <c:if test="${not empty mensagem}">
            <div class="auth-msg auth-ok"><c:out value="${mensagem}"/></div>
        </c:if>
        <c:if test="${not empty erro}">
            <div class="auth-msg auth-erro"><c:out value="${erro}"/></div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}${URL_BASE}/RecuperarSenhaControlador">
            <input type="hidden" name="opcao" value="solicitar">
            <div class="form-group">
                <label for="email">E-mail</label>
                <input type="email" id="email" name="email" required maxlength="255"
                       autocomplete="email" autofocus value="<c:out value="${email}"/>">
            </div>
            <button type="submit" class="btn-salvar">Enviar link de recuperação</button>
        </form>

        <div class="auth-links">
            <a href="${pageContext.request.contextPath}/login.jsp">Voltar ao login</a>
        </div>
    </div>
</div>

<footer class="footer">
    <p>© 2026 Atacadão - Todos os direitos reservados</p>
</footer>
</body>
</html>
