/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.supermercado.modelo.dao;

import br.edu.ifsuldeminas.supermercado.entidade.Itensvenda;
import br.edu.ifsuldeminas.supermercado.entidade.Produto;
import br.edu.ifsuldeminas.supermercado.entidade.Venda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author 12421698650
 */

public class ItensvendaDao {

    public boolean salvar(Itensvenda item) {
        String sql = "INSERT INTO itensvenda (codVenda, codProduto, quantidade, precoUnitario) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getVenda().getCodVenda());
            stmt.setInt(2, item.getProduto().getCodigoProduto());
            stmt.setInt(3, item.getQuantidade());
            stmt.setDouble(4, item.getPrecoUnitario());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean alterar(Itensvenda item) {
        String sql = "UPDATE itensvenda SET codVenda = ?, codProduto = ?, quantidade = ?, precoUnitario = ? WHERE codItensVenda = ?";
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getVenda().getCodVenda());
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

    public boolean excluir(Itensvenda item) {
        String sql = "DELETE FROM itensvenda WHERE codItensVenda = ?";
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Itensvenda> buscarTodos() {
        List<Itensvenda> lista = new ArrayList<>();
        String sql = "SELECT iv.*, p.nome AS nome FROM itensvenda iv " +
                     "JOIN produto p ON iv.codProduto = p.codProduto ORDER BY iv.codItensVenda DESC";
        try (Connection conn = ConnectionFactory.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Itensvenda item = new Itensvenda();
                item.setId(rs.getInt("codItensVenda"));
                
                Venda v = new Venda();
                v.setCodVenda(rs.getInt("codVenda"));
                item.setVenda(v);
                
                Produto p = new Produto();
                p.setCodigoProduto(rs.getInt("codProduto"));
                p.setNomeProduto(rs.getString("nome"));
                item.setProduto(p);
                
                item.setQuantidade(rs.getInt("quantidade"));
                item.setPrecoUnitario(rs.getDouble("precoUnitario"));
                lista.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}