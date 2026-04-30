/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.supermercado.modelo.dao;

/**
 *
 * @author 12421698650
 */
import br.edu.ifsuldeminas.supermercado.entidade.FormaPagamento;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class FormaPagamentoDao extends GenericoDAO<FormaPagamento>{

    public void salvar(FormaPagamento obj){
        String sql ="INSERT INTO formapagamento(descricao) VALUES(?)";
        save(sql, obj.getDescricaoFormaPagamento());
    }

    public void alterar(FormaPagamento obj){
        String sql ="UPDATE formapagamento SET descricao=? WHERE codFormaPagamento=?";
        save(sql, obj.getDescricaoFormaPagamento(), obj.getCodigoFormaPagamento());
    }

    public void excluir(FormaPagamento obj){
        String sql ="DELETE FROM formapagamento WHERE codFormaPagamento=?";
        save(sql, obj.getCodigoFormaPagamento());
    }

    public List<FormaPagamento> buscarTodos(){
        String sql = "SELECT * FROM formapagamento";
        return buscarTodos(sql, new FormaPagamentoRowMapper());
    }

    private static class FormaPagamentoRowMapper implements RowMapper<FormaPagamento>{

        @Override
        public FormaPagamento mapRow(ResultSet rs) throws SQLException {
            FormaPagamento obj = new FormaPagamento();
            obj.setCodigoFormaPagamento(rs.getInt("codFormaPagamento"));
            obj.setDescricaoFormaPagamento(rs.getString("descricao"));
            return obj;
        }
    }
}