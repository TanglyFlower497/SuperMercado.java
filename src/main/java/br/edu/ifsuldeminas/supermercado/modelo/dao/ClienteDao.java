/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.supermercado.modelo.dao;
import br.edu.ifsuldeminas.supermercado.entidade.Cliente;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class ClienteDao extends GenericoDAO<Cliente>{
     
    public void salvar(Cliente obj){
        String sql ="INSERT INTO cliente(nome, cpf, telefone, email) VALUES(?,?,?,?)";
        save(sql, obj.getNomeCliente(), obj.getCpfCliente(), obj.getTelefoneCliente(), obj.getEmailCliente());
    }

    public void alterar(Cliente obj){
        String sql ="UPDATE cliente SET nome=?, cpf=?, telefone=?, email=? WHERE codCliente=?";
        save(sql, obj.getNomeCliente(), obj.getCpfCliente(), obj.getTelefoneCliente(), obj.getEmailCliente(), obj.getCodigoCliente());
    }

    public void excluir(Cliente obj){
        String sql ="DELETE FROM cliente WHERE codCliente=?";
        save(sql, obj.getCodigoCliente());
    }

    public List<Cliente> buscarTodos(){
        String sql = "SELECT * FROM CLIENTE";
        return buscarTodos(sql, new ClienteRowMapper());
    }

    private static class ClienteRowMapper implements RowMapper<Cliente>{

        @Override
        public Cliente mapRow(ResultSet rs) throws SQLException {
            Cliente obj = new Cliente();
            obj.setCodigoCliente(rs.getInt("codCliente"));
            obj.setNomeCliente(rs.getString("nome"));
            obj.setCpfCliente(rs.getString("cpf"));
            obj.setTelefoneCliente(rs.getString("telefone"));
            obj.setEmailCliente(rs.getString("email"));
            return obj;
        }
    }
}
