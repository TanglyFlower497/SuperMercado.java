package br.edu.ifsuldeminas.supermercado.modelo.dao;

import br.edu.ifsuldeminas.supermercado.entidade.Funcionario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FuncionarioDao extends GenericoDAO<Funcionario> {

    public void salvar(Funcionario obj){
        String sql ="INSERT INTO funcionario(nome, cargo, salario) VALUES(?,?,?)";
        save(sql, obj.getNomeFuncionario(), obj.getCargoFuncionario(), obj.getSalarioFuncionario());
    }

    public void alterar(Funcionario obj){
        String sql ="UPDATE funcionario SET nome=?, cargo=?, salario=? WHERE codFuncionario=?";
        save(sql, obj.getNomeFuncionario(), obj.getCargoFuncionario(), obj.getSalarioFuncionario(), obj.getCodigoFuncionario());
    }

    public void excluir(Funcionario obj){
        String sql ="DELETE FROM funcionario WHERE codFuncionario=?";
        save(sql, obj.getCodigoFuncionario());
    }

    public List<Funcionario> buscarTodos(){
        String sql = "SELECT * FROM funcionario";
        return buscarTodos(sql, new FuncionarioRowMapper());
    }

    public Funcionario buscarPorId(int id){
        String sql = "SELECT * FROM funcionario WHERE codFuncionario=?";
        return buscarPorId(sql, new FuncionarioRowMapper(), id);
    }

    private static class FuncionarioRowMapper implements RowMapper<Funcionario>{

        @Override
        public Funcionario mapRow(ResultSet rs) throws SQLException {
            Funcionario obj = new Funcionario();
            obj.setCodigoFuncionario(rs.getInt("codFuncionario"));
            obj.setNomeFuncionario(rs.getString("nome"));
            obj.setCargoFuncionario(rs.getString("cargo"));
            obj.setSalarioFuncionario(rs.getDouble("salario"));
            return obj;
        }
    }
}