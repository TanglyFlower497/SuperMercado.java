<%--
    Document   : CadastroFuncionario
    Created on : 23 de abr. de 2026
    Author     : vcart
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cadastro de Funcionários</title>
    </head>
    <%@include file="menu.jsp" %>
    <body>
        <h1>Funcionário</h1>

        <form name="cadastro" method="get" action="${pageContext.request.contextPath}${URL_BASE}/FuncionarioControlador">
            <input type="hidden" name="opcao" value="${opcao}"/>
            <input type="hidden" name="codigoFuncionario" value="${codigoFuncionario}"/>

            <p>
                <label>Nome:</label> 
                <input type="text" name="nomeFuncionario" value="${nomeFuncionario}" size="40">
            </p>

            <p>
                <label>Cargo:</label> 
                <input type="text" name="cargoFuncionario" value="${cargoFuncionario}" size="30">
            </p>

            <p>
                <label>Salário:</label> 
                <input type="text" name="salarioFuncionario" value="${salarioFuncionario}" size="15">
            </p>

            <input type="submit" value="Salvar" name="Salvar" style="float:left; margin-right: 3px"/>
        </form>

        <form id="cadastroForm" name="cadastro" method="get" action="${pageContext.request.contextPath}${URL_BASE}/FuncionarioControlador">
            <input type="submit" value="Cancelar" name="btnCancelar">
            <input type="hidden" name="opcao" value="cancelar">
        </form>

        <h2>${mensagem}</h2>

        <table border="1">
            <c:if test="${not empty listaFuncionario}">
                <tr>
                    <th>CÓDIGO</th>
                    <th>NOME</th>
                    <th>CARGO</th>
                    <th>SALÁRIO</th>
                    <th>ALTERAR</th>
                    <th>EXCLUIR</th>
                </tr>  
            </c:if>

            <c:forEach var="f" items="${listaFuncionario}">
                <tr>
                    <td>${f.codigoFuncionario}</td>
                    <td>${f.nomeFuncionario}</td>
                    <td>${f.cargoFuncionario}</td>
                    <td>${f.salarioFuncionario}</td>

                    <td>
                        <form name="cadastro" method="get" action="${pageContext.request.contextPath}${URL_BASE}/FuncionarioControlador">
                            <input type="hidden" name="opcao" value="enviarAlterar"/>
                            <input type="hidden" name="codigoFuncionario" value="${f.codigoFuncionario}"/>
                            <input type="hidden" name="nomeFuncionario" value="${f.nomeFuncionario}"/>
                            <input type="hidden" name="cargoFuncionario" value="${f.cargoFuncionario}"/>
                            <input type="hidden" name="salarioFuncionario" value="${f.salarioFuncionario}"/>
                            <button type="submit">Alterar</button>
                        </form>
                    </td>

                    <td>
                        <form name="cadastro" method="get" action="${pageContext.request.contextPath}${URL_BASE}/FuncionarioControlador">
                            <input type="hidden" name="opcao" value="enviarExcluir"/>
                            <input type="hidden" name="codigoFuncionario" value="${f.codigoFuncionario}"/>
                            <input type="hidden" name="nomeFuncionario" value="${f.nomeFuncionario}"/>
                            <input type="hidden" name="cargoFuncionario" value="${f.cargoFuncionario}"/>
                            <input type="hidden" name="salarioFuncionario" value="${f.salarioFuncionario}"/>
                            <button type="submit">Excluir</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>

    </body>
</html>