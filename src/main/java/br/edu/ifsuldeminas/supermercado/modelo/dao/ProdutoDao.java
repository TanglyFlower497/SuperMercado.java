/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.supermercado.modelo.dao;

/**
 *
 * @author 12421698650
 */

import br.edu.ifsuldeminas.supermercado.entidade.Produto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProdutoDao extends GenericoDAO<Produto>{

    public void salvar(Produto obj){
        String sql ="INSERT INTO produto(nome, preco, estoque, id_categoria) VALUES(?,?,?,?)";
        save(sql, obj.getNomeProduto(), obj.getPrecoProduto(), obj.getEstoqueProduto(), obj.getCodigoCategoria());
    }

    public void alterar(Produto obj){
        String sql ="UPDATE produto SET nome=?, preco=?, estoque=?, id_categoria=? WHERE codProduto=?";
        save(sql, obj.getNomeProduto(), obj.getPrecoProduto(), obj.getEstoqueProduto(), obj.getCodigoCategoria(), obj.getCodigoProduto());
    }

    public void excluir(Produto obj){
        String sql ="DELETE FROM produto WHERE codProduto=?";
        save(sql, obj.getCodigoProduto());
    }

    public List<Produto> buscarTodos(){
        String sql = "SELECT p.*, c.nome AS nomeCategoria " +
                     "FROM produto p LEFT JOIN categoria c ON p.id_categoria = c.codCategoria";
        return buscarTodos(sql, new ProdutoRowMapper());
    }

    private static class ProdutoRowMapper implements RowMapper<Produto>{

        @Override
        public Produto mapRow(ResultSet rs) throws SQLException {
            Produto obj = new Produto();
            obj.setCodigoProduto(rs.getInt("codProduto"));
            obj.setNomeProduto(rs.getString("nome"));
            obj.setPrecoProduto(rs.getDouble("preco"));
            obj.setEstoqueProduto(rs.getInt("estoque"));
            obj.setCodigoCategoria(rs.getInt("id_categoria"));
            obj.setNomeCategoria(rs.getString("nomeCategoria"));
            return obj;
        }
    }
}