<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="head.jsp" %>
    <title>Gestão de Itens da Compra</title>
    <style>
     .container-duas-caixas {
            display: flex;
            gap: 20px;
            margin-top: 15px;
            margin-bottom: 20px;
            flex-wrap: wrap;
        }
        .caixa-grande {
            flex: 1;
            min-width: 280px;
            border: 2px solid #0056b3;
            border-radius: 8px;
            padding: 20px;
            background-color: #f8f9fa;
        }
        .caixa-titulo {
            font-size: 1.1rem;
            font-weight: bold;
            color: #0056b3;
            margin-bottom: 12px;
            border-bottom: 2px solid #0056b3;
            padding-bottom: 4px;
        }
        .painel-botoes {
            margin-top: 15px;
            display: flex;
            flex-direction: column;
            gap: 10px;
            width: 100%;
        }
        .painel-botoes .btn {
            width: 100%;
            text-align: center;
        }
        select, input {
            width: 100%;
            padding: 8px;
            margin-top: 4px;
            margin-bottom: 12px;
            border-radius: 4px;
            border: 1px solid #ccc;
            box-sizing: border-box;
        }
        .table-responsive {
            width: 100%;
            overflow-x: auto;
            margin-bottom: 20px;
        }
        table {
            min-width: 600px;
        }
        footer {
            width: 100%;
            margin-bottom: 20px;
            box-sizing: border-box;
        }
    </style>
</head>
<body>
    <%@include file="menu.jsp" %>
    
    <div class="container-itens">
        <h1 class="titulo-pagina">Gestão de Itens da Compra</h1>
        
        <form name="cadastro" method="get" action="${pageContext.request.contextPath}${URL_BASE}/ItenscompraControlador">
            <input type="hidden" name="opcao" value="${opcao}"/>
            <input type="hidden" name="codigoItenscompra" value="${codigoItenscompra}"/>

            <div class="caixas-container">
                <!-- CAIXA 1: Produto e Valores -->
                <div class="caixa">
                    <h3>Item a Comprar (Produto)</h3>
                    
                    <div class="form-group">
                        <label>Selecione o Produto:</label>
                        <select name="codigoProduto" class="form-control form-listbox" size="8" required>
                            <c:forEach var="produto" items="${listaProduto}">
                                <option value="${produto.codigoProduto}" ${produto.codigoProduto == codigoProduto ? 'selected' : ''}>
                                    [ID: ${produto.codigoProduto}] ${produto.nomeProduto}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group">
                        <label>Quantidade:</label>
                        <input type="number" class="form-control" required name="quantidadeItenscompra" value="${quantidadeItenscompra}" />
                    </div>

                    <div class="form-group">
                        <label>Preço Unitário (R$):</label>
                        <input type="number" step="0.01" class="form-control" required name="precoUnitarioItenscompra" value="${precoUnitarioItenscompra}" placeholder="0.00" />
                    </div>
                </div>

                <!-- CAIXA 2: Seleção da Compra -->
                <div class="caixa">
                    <h3>Compra Realizada</h3>
                    
                    <div class="form-group">
                        <label>Selecione a Compra:</label>
                        <select name="codigoCompra" class="form-control form-listbox" size="8" required>
                            <c:forEach var="compra" items="${listaCompra}">
                                <option value="${compra.codCompra}" ${compra.codCompra == codigoCompra ? 'selected' : ''}>
                                    Compra #${compra.codCompra} | Data: ${compra.data}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>

            <!-- Botão Salvar dentro do formulário principal -->
            <input type="submit" value="Salvar" style="float:left; margin-bottom: 6px"/>
        </form>

        <!-- Formulário separado para o botão Cancelar -->
        <form method="get" action="${pageContext.request.contextPath}${URL_BASE}/ItenscompraControlador">
            <input type="submit" value="Cancelar" style=" background-color:#cd5c5c;">
            <input type="hidden" name="opcao" value="cancelar">
        </form>

        <c:if test="${not empty mensagem}">
            <h4 style="color: green; text-align: center; margin-bottom: 20px;">${mensagem}</h4>
        </c:if>

        <h3 class="tabela-titulo">Itens Cadastrados</h3>
        
        <!-- Tabela -->
        <div class="table-responsive">
            <table>
                <thead>
                    <tr>
                        <th>Código</th>
                        <th>Compra #</th>
                        <th>Produto</th>
                        <th>Qtd</th>
                        <th>Preço Unit.</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${listaItenscompra}">
                        <tr>
                            <td>${item.id}</td>
                            <td>${item.compra.codCompra}</td>
                            <td>${item.produto.nomeProduto}</td>
                            <td>${item.quantidade}</td>
                            <td>R$ ${item.precoUnitario}</td>
                            <td>
                                <form class="acoes-form" method="get" action="${pageContext.request.contextPath}${URL_BASE}/ItenscompraControlador">
                                    <input type="hidden" name="opcao" value="enviarAlterar"/>
                                    <input type="hidden" name="codigoItenscompra" value="${item.id}"/>
                                    <input type="hidden" name="codigoCompra" value="${item.compra.codCompra}"/>
                                    <input type="hidden" name="codigoProduto" value="${item.produto.codigoProduto}"/>
                                    <input type="hidden" name="quantidadeItenscompra" value="${item.quantidade}"/>
                                    <input type="hidden" name="precoUnitarioItenscompra" value="${item.precoUnitario}"/>
                                    <button type="submit" >Alterar</button>
                                </form>
                                
                                <form class="acoes-form" method="get" action="${pageContext.request.contextPath}${URL_BASE}/ItenscompraControlador">
                                    <input type="hidden" name="opcao" value="enviarExcluir"/>
                                    <input type="hidden" name="codigoItenscompra" value="${item.id}"/>
                                    <input type="hidden" name="codigoCompra" value="${item.compra.codCompra}"/>
                                    <input type="hidden" name="codigoProduto" value="${item.produto.codigoProduto}"/>
                                    <input type="hidden" name="quantidadeItenscompra" value="${item.quantidade}"/>
                                    <input type="hidden" name="precoUnitarioItenscompra" value="${item.precoUnitario}"/>
                                    <button type="submit" >Excluir</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <footer>
        <p>© 2026 Atacadão - Todos os direitos reservados</p>
    </footer>
</body>
</html>