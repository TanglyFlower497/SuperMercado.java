/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.supermercado.modelo.dao;

/**
 *
 * @author 12421698650
 */
import br.edu.ifsuldeminas.supermercado.entidade.Fornecedor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FornecedorDao extends GenericoDAO<Fornecedor>{

    public void salvar(Fornecedor obj){
        String sql ="INSERT INTO fornecedor(nome, cnpj, telefone, email) VALUES(?,?,?,?)";
        save(sql, obj.getNomeFornecedor(), obj.getCnpjFornecedor(), obj.getTelefoneFornecedor(), obj.getEmailFornecedor());
    }

    public void alterar(Fornecedor obj){
        String sql ="UPDATE fornecedor SET nome=?, cnpj=?, telefone=?, email=? WHERE codFornecedor=?";
        save(sql, obj.getNomeFornecedor(), obj.getCnpjFornecedor(), obj.getTelefoneFornecedor(), obj.getEmailFornecedor(), obj.getCodigoFornecedor());
    }

    public void excluir(Fornecedor obj){
        String sql ="DELETE FROM fornecedor WHERE codFornecedor=?";
        save(sql, obj.getCodigoFornecedor());
    }

    public List<Fornecedor> buscarTodos(){
        String sql = "SELECT * FROM fornecedor";
        return buscarTodos(sql, new FornecedorRowMapper());
    }

    private static class FornecedorRowMapper implements RowMapper<Fornecedor>{

        @Override
        public Fornecedor mapRow(ResultSet rs) throws SQLException {
            Fornecedor obj = new Fornecedor();
            obj.setCodigoFornecedor(rs.getInt("codFornecedor"));
            obj.setNomeFornecedor(rs.getString("nome"));
            obj.setCnpjFornecedor(rs.getString("cnpj"));
            obj.setTelefoneFornecedor(rs.getString("telefone"));
            obj.setEmailFornecedor(rs.getString("email"));
            return obj;
        }
    }
}