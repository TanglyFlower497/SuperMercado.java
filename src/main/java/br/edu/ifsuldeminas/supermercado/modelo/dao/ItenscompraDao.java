/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.supermercado.modelo.dao;
import br.edu.ifsuldeminas.supermercado.entidade.Itenscompra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItenscompraDao {
    private Connection conn;

    public ItenscompraDao(Connection conn) {
        this.conn = conn;
    }

    public void inserir(Itenscompra item) throws SQLException {
        String sql = "INSERT INTO itenscompra (produto_id, quantidade, preco_unitario) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, item.getProdutoId());
        stmt.setInt(2, item.getQuantidade());
        stmt.setDouble(3, item.getPrecoUnitario());
        stmt.execute();
        stmt.close();
    }

    public List<Itenscompra> listar() throws SQLException {
        List<Itenscompra> lista = new ArrayList<>();
        String sql = "SELECT * FROM itenscompra";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Itenscompra item = new Itenscompra();
            item.setId(rs.getInt("id"));
            item.setProdutoId(rs.getInt("produto_id"));
            item.setQuantidade(rs.getInt("quantidade"));
            item.setPrecoUnitario(rs.getDouble("preco_unitario"));
            lista.add(item);
        }

        rs.close();
        stmt.close();
        return lista;
    }

    public void atualizar(Itenscompra item) throws SQLException {
        String sql = "UPDATE itenscompra SET produto_id=?, quantidade=?, preco_unitario=? WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, item.getProdutoId());
        stmt.setInt(2, item.getQuantidade());
        stmt.setDouble(3, item.getPrecoUnitario());
        stmt.setInt(4, item.getId());
        stmt.execute();
        stmt.close();
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM itenscompra WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.execute();
        stmt.close();
    }
}