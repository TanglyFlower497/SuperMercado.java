/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.supermercado.modelo.dao;

import br.edu.ifsuldeminas.supermercado.entidade.Categoria;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoriaDao extends GenericoDAO<Categoria>{

    public void salvar(Categoria obj){
        String sql ="INSERT INTO categoria(nome, descricao) VALUES(?,?)";
        save(sql, obj.getNomeCategoria(), obj.getDescricaoCategoria());
    }

    public void alterar(Categoria obj){
        String sql ="UPDATE categoria SET nome=?, descricao=? WHERE codCategoria=?";
        save(sql, obj.getNomeCategoria(), obj.getDescricaoCategoria(), obj.getCodigoCategoria());
    }

    public void excluir(Categoria obj){
        String sql ="DELETE FROM categoria WHERE codCategoria=?";
        save(sql, obj.getCodigoCategoria());
    }

    public List<Categoria> buscarTodos(){
        String sql = "SELECT * FROM categoria";
        return buscarTodos(sql, new CategoriaRowMapper());
    }

    private static class CategoriaRowMapper implements RowMapper<Categoria>{

        @Override
        public Categoria mapRow(ResultSet rs) throws SQLException {
            Categoria obj = new Categoria();
            obj.setCodigoCategoria(rs.getInt("codCategoria"));
            obj.setNomeCategoria(rs.getString("nome"));
            obj.setDescricaoCategoria(rs.getString("descricao"));
            return obj;
        }
    }
}
