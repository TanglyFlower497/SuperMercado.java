package br.edu.ifsuldeminas.supermercado.relatorio;

import br.edu.ifsuldeminas.supermercado.modelo.dao.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RelatorioDao {

    // Relatório 06: Folha Salarial Detalhada (RH)
    public List<Map<String, Object>> buscarFolhaSalarial() throws SQLException {
        String sql = "SELECT codFuncionario, nome, COALESCE(cpf, 'Não informado') AS cpf, "
                   + "cargo, COALESCE(salario, 0) AS salario, dataAdmissao "
                   + "FROM funcionario ORDER BY salario DESC";
        return executarConsulta(sql);
    }

    // Relatório 06 (Aba 2): Resumo de Custos por Cargo
    public List<Map<String, Object>> buscarResumoPorCargo() throws SQLException {
        String sql = "SELECT cargo, COUNT(*) AS qtdFuncionarios, "
                   + "COALESCE(SUM(salario), 0) AS totalSalarios, "
                   + "COALESCE(AVG(salario), 0) AS mediaSalarial "
                   + "FROM funcionario GROUP BY cargo ORDER BY totalSalarios DESC";
        return executarConsulta(sql);
    }

    // Relatório 07: Compras e Concentração por Fornecedor
    public List<Map<String, Object>> buscarComprasPorFornecedor() throws SQLException {
        String sql = "SELECT f.codFornecedor, f.nome, f.cnpj, COALESCE(f.cidade, '-') AS cidade, "
                   + "COALESCE(f.uf, '-') AS uf, COUNT(c.codCompra) AS qtdPedidos, "
                   + "COALESCE(SUM(c.total), 0) AS totalComprado, "
                   + "COALESCE(AVG(c.total), 0) AS ticketMedio, MAX(c.data) AS ultimaCompra "
                   + "FROM fornecedor f LEFT JOIN compra c ON f.codFornecedor = c.codFornecedor "
                   + "GROUP BY f.codFornecedor, f.nome, f.cnpj, f.cidade, f.uf "
                   + "ORDER BY totalComprado DESC";
        return executarConsulta(sql);
    }

    // Relatório 08: Curva ABC de Clientes e Fidelização
    public List<Map<String, Object>> buscarCurvaAbcClientes() throws SQLException {
        String sql = "SELECT cl.codCliente, cl.nome, cl.cpf, COALESCE(cl.telefone, '-') AS telefone, "
                   + "COALESCE(cl.email, '-') AS email, COUNT(v.codVenda) AS qtdCompras, "
                   + "COALESCE(SUM(v.total - COALESCE(v.desconto, 0)), 0) AS totalLiquido, "
                   + "MAX(v.data) AS ultimaCompra, "
                   + "DATEDIFF(CURDATE(), MAX(v.data)) AS diasInativo "
                   + "FROM cliente cl INNER JOIN venda v ON cl.codCliente = v.codCliente "
                   + "GROUP BY cl.codCliente, cl.nome, cl.cpf, cl.telefone, cl.email "
                   + "ORDER BY totalLiquido DESC";
        return executarConsulta(sql);
    }

    // Relatório 09: Margem de Lucro Bruta e Precificação
    public List<Map<String, Object>> buscarMargemProdutos() throws SQLException {
        String sql = "SELECT p.codProduto, p.codigoBarras, p.nome, c.nome AS categoria, "
                   + "COALESCE(p.preco, 0) AS preco, COALESCE(p.custo, 0) AS custo, "
                   + "COALESCE(p.estoque, 0) AS estoque, COALESCE(p.estoqueMinimo, 0) AS estoqueMinimo "
                   + "FROM produto p INNER JOIN categoria c ON p.codCategoria = c.codCategoria "
                   + "ORDER BY c.nome, p.nome";
        return executarConsulta(sql);
    }

    // Relatório 10: Curva ABC de Produtos Mais Vendidos e Giro
    public List<Map<String, Object>> buscarCurvaAbcProdutos() throws SQLException {
        String sql = "SELECT p.codProduto, p.codigoBarras, p.nome, c.nome AS categoria, "
                   + "COALESCE(SUM(iv.quantidade), 0) AS qtdVendida, "
                   + "COALESCE(SUM(iv.subtotal), 0) AS receitaGerada, "
                   + "COALESCE(p.estoque, 0) AS estoque "
                   + "FROM produto p "
                   + "INNER JOIN categoria c ON p.codCategoria = c.codCategoria "
                   + "LEFT JOIN itensvenda iv ON p.codProduto = iv.codProduto "
                   + "GROUP BY p.codProduto, p.codigoBarras, p.nome, c.nome, p.estoque "
                   + "ORDER BY receitaGerada DESC, qtdVendida DESC";
        return executarConsulta(sql);
    }

    private List<Map<String, Object>> executarConsulta(String sql) throws SQLException {
        List<Map<String, Object>> lista = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ResultSetMetaData meta = rs.getMetaData();
            int cols = meta.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                for (int i = 1; i <= cols; i++) {
                    row.put(meta.getColumnLabel(i), rs.getObject(i));
                }
                lista.add(row);
            }
        }
        return lista;
    }
}
