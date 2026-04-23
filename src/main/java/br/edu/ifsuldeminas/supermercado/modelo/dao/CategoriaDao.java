/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.supermercado.modelo.dao;

import br.edu.ifsuldeminas.supermercado.entidade.Categoria;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author 12421698650
 */
public class CategoriaDao {
     public void salvar(Categoria objCategoria){
        String sql ="INSERT INTO CIDADE(NOME,UF) VALUES(?,?)";
        save(sql, objCategoria.getNome(), objCidade.getUfCidade());
    }
    public void alterar(Cidade objCidade){
        String sql ="UPDATE CIDADE SET NOME=?,UF=? WHERE CODIGO=?";
        save(sql, objCidade.getNomeCidade(), objCidade.getUfCidade(),objCidade.getCodigoCidade());
    }
    
    public void excluir(Cidade objCidade){
        String sql ="DELETE FROM CIDADE WHERE CODIGO=? ";
        save(sql,objCidade.getCodigoCidade());
    }
    
    public List<Cidade> buscarTodasCidades(){
        String sql = "SELECT * FROM CIDADE";
        return buscarTodos(sql, new CidadeRowMapper());
    }
    
    public Cidade buscarCidadeOrId(int id){
        String sql = "SELECT * FROM CIDADE WHERE CODIGO=?";
        return buscarPorId(sql, new CidadeRowMapper(), id);
    }
    
    private static class CidadeRowMapper implements RowMapper<Cidade>{

        @Override
        public Cidade mapRow(ResultSet rs) throws SQLException {
            Cidade objCidade = new Cidade();
            objCidade.setCodigoCidade(rs.getInt("CODIGO"));
            objCidade.setNomeCidade(rs.getString("NOME"));
            objCidade.setUfCidade(rs.getString("UF"));
           
            System.out.println("Mapeando o objeto: "+objCidade.toString());
            
            return objCidade;
        }
    }   
}
