/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.supermercado.modelo.dao;

import br.edu.ifsuldeminas.supermercado.entidade.Itenscompra;
import br.edu.ifsuldeminas.supermercado.entidade.Produto;
import br.edu.ifsuldeminas.supermercado.entidade.Compra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItenscompraDao {

    public boolean salvar(Itenscompra item) {
        String sql = "INSERT INTO itenscompra (codCompra, codProduto, quantidade, precoUnitario) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getCompra().getCodCompra());
            stmt.setInt(2, item.getProduto().getCodigoProduto());
            stmt.setInt(3, item.getQuantidade());
            stmt.setDouble(4, item.getPrecoUnitario());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean alterar(Itenscompra item) {
        String sql = "UPDATE itenscompra SET codCompra = ?, codProduto = ?, quantidade = ?, precoUnitario = ? WHERE codItensCompra = ?";
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getCompra().getCodCompra());
            stmt.setInt(2, item.getProduto().getCodigoProduto());
            stmt.setInt(3, item.getQuantidade());
            stmt.setDouble(4, item.getPrecoUnitario());
            stmt.setInt(5, item.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(Itenscompra item) {
        String sql = "DELETE FROM itenscompra WHERE codItensCompra = ?";
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Itenscompra> buscarTodos() {
        List<Itenscompra> lista = new ArrayList<>();
        String sql = "SELECT ic.*, p.nome AS nomeProduto FROM itenscompra ic " +
                     "JOIN produto p ON ic.codProduto = p.codProduto ORDER BY ic.codItensCompra DESC";
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Itenscompra item = new Itenscompra();
                item.setId(rs.getInt("codItensCompra"));
                
                Compra c = new Compra();
                c.setCodCompra(rs.getInt("codCompra"));
                item.setCompra(c);
                
                Produto p = new Produto();
                p.setCodigoProduto(rs.getInt("codProduto"));
                p.setNomeProduto(rs.getString("nomeProduto"));
                item.setProduto(p);
                
                item.setQuantidade(rs.getInt("quantidade"));
                item.setPrecoUnitario(rs.getDouble("precoUnitario"));
                item.setSubtotal(rs.getDouble("subtotal")); // Valor retornado pelas Triggers
                
                lista.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}