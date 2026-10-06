<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
    <%@ include file="head.jsp" %>
    <title>Login - Atacado</title>
</head>
<body>

<div class="auth-wrapper">
    <div class="auth-card">
        <h1>Atacado</h1>
        <p class="auth-sub">Acesse o sistema</p>

        <c:choose>
            <c:when test="${param.aviso == 'login'}"><div class="auth-msg auth-info">Faça login para continuar.</div></c:when>
            <c:when test="${param.aviso == 'logout'}"><div class="auth-msg auth-ok">Você saiu do sistema.</div></c:when>
            <c:when test="${param.aviso == 'cadastrado'}"><div class="auth-msg auth-ok">Cadastro realizado! Faça login.</div></c:when>
            <c:when test="${param.aviso == 'senha-alterada'}"><div class="auth-msg auth-ok">Senha alterada com sucesso. Entre com a nova senha.</div></c:when>
        </c:choose>

        <c:if test="${not empty erro}">
            <div class="auth-msg auth-erro"><c:out value="${erro}"/></div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}${URL_BASE}/LoginControlador">
            <div class="form-group">
                <label for="nomeUsuario">Usuário</label>
                <input type="text" id="nomeUsuario" name="nomeUsuario" required maxlength="50"
                       autocomplete="username" autofocus value="<c:out value="${nomeUsuario}"/>">
            </div>
            <div class="form-group">
                <label for="senha">Senha</label>
                <input type="password" id="senha" name="senha" required maxlength="72"
                       autocomplete="current-password">
            </div>
            <button type="submit" class="btn-salvar">Entrar</button>
        </form>

        <div class="auth-links">
            <a href="${pageContext.request.contextPath}/EsqueciSenha.jsp">Esqueci minha senha</a>
            <a href="${pageContext.request.contextPath}/CadastrarUsuario.jsp">Criar conta</a>
        </div>
    </div>
</div>

<footer class="footer">
    <p>© 2026 Atacadão - Todos os direitos reservados</p>
</footer>
</body>
</html>
