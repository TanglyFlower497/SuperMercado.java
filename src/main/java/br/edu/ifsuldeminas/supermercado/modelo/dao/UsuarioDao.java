package br.edu.ifsuldeminas.supermercado.modelo.dao;

import br.edu.ifsuldeminas.supermercado.entidade.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Acesso à tabela "usuario".
 *
 * As leituras usam o GenericoDAO do projeto. As escritas usam JDBC direto porque o
 * GenericoDAO.save() engole as exceções e devolve void, e aqui precisamos saber se
 * o INSERT/UPDATE realmente funcionou (ex.: nome de usuário duplicado).
 */
public class UsuarioDao extends GenericoDAO<Usuario> {

    /** @return true se cadastrou; false se falhou (ex.: nome de usuário já existe). */
    public boolean salvar(Usuario obj) {
        String sql = "INSERT INTO usuario(nomeusuario, senhahash, email) VALUES(?,?,?)";
        try {
            return executarAtualizacao(sql, obj.getNomeUsuario(), obj.getSenhaHash(), obj.getEmail()) > 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            return false; // UNIQUE de nomeusuario
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Usuario buscarPorNomeUsuario(String nomeUsuario) {
        String sql = "SELECT * FROM usuario WHERE nomeusuario=?";
        return buscarPorId(sql, new UsuarioRowMapper(), nomeUsuario);
    }

    public Usuario buscarPorEmail(String email) {
        String sql = "SELECT * FROM usuario WHERE email=? LIMIT 1";
        return buscarPorId(sql, new UsuarioRowMapper(), email);
    }

    /** @param novoHash hash BCrypt já calculado (nunca a senha em texto puro). */
    public boolean atualizarSenha(int idUsuario, String novoHash) {
        String sql = "UPDATE usuario SET senhahash=? WHERE id=?";
        try {
            return executarAtualizacao(sql, novoHash, idUsuario) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private int executarAtualizacao(String sql, Object... parametros) throws SQLException {
        try (Connection con = ConnectionFactory.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < parametros.length; i++) {
                ps.setObject(i + 1, parametros[i]);
            }
            return ps.executeUpdate();
        }
    }

    private static class UsuarioRowMapper implements RowMapper<Usuario> {

        @Override
        public Usuario mapRow(ResultSet rs) throws SQLException {
            Usuario obj = new Usuario();
            obj.setId(rs.getInt("id"));
            obj.setNomeUsuario(rs.getString("nomeusuario"));
            obj.setSenhaHash(rs.getString("senhahash"));
            obj.setEmail(rs.getString("email"));
            return obj;
        }
    }
}
