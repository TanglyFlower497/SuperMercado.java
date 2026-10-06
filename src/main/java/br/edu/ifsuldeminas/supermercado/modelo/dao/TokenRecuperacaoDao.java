package br.edu.ifsuldeminas.supermercado.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Tokens de recuperação de senha (tabela "token_recuperacao").
 * Só o SHA-256 do token é guardado. O prazo é calculado pelo próprio MySQL (NOW()),
 * evitando diferenças de fuso horário entre a JVM e o banco.
 */
public class TokenRecuperacaoDao {

    private final ConnectionFactory fabrica = ConnectionFactory.getInstance();

    /**
     * Cria um novo token para o usuário e invalida os anteriores ainda pendentes
     * (só o link do e-mail mais recente funciona). Tudo em uma transação.
     */
    public boolean criar(int idUsuario, String tokenHash, int validadeMinutos) {
        try (Connection con = fabrica.getConnection()) {
            con.setAutoCommit(false);
            try {
                try (PreparedStatement ps = con.prepareStatement(
                        "UPDATE token_recuperacao SET usado = 1 WHERE usuario_id = ? AND usado = 0")) {
                    ps.setInt(1, idUsuario);
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO token_recuperacao (usuario_id, token_hash, expira_em) "
                        + "VALUES (?, ?, DATE_ADD(NOW(), INTERVAL ? MINUTE))")) {
                    ps.setInt(1, idUsuario);
                    ps.setString(2, tokenHash);
                    ps.setInt(3, validadeMinutos);
                    ps.executeUpdate();
                }
                con.commit();
                return true;
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** O token existe, ainda não foi usado e não expirou? */
    public boolean tokenValido(String tokenHash) {
        String sql = "SELECT 1 FROM token_recuperacao WHERE token_hash = ? AND usado = 0 AND expira_em > NOW()";
        try (Connection con = fabrica.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tokenHash);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Troca a senha e invalida o token ATOMICAMENTE (tudo ou nada).
     * O "SELECT ... FOR UPDATE" trava a linha do token: se duas requisições tentarem usar
     * o mesmo link ao mesmo tempo, a segunda espera a primeira terminar e então já o encontra
     * como usado, ou seja, o token nunca é reutilizável.
     *
     * @param novoHashSenha hash BCrypt da nova senha
     * @return true se a senha foi alterada; false se o token é inválido/expirado/usado ou deu erro
     */
    public boolean redefinirSenha(String tokenHash, String novoHashSenha) {
        try (Connection con = fabrica.getConnection()) {
            con.setAutoCommit(false);
            try {
                int idUsuario;
                try (PreparedStatement ps = con.prepareStatement(
                        "SELECT usuario_id FROM token_recuperacao "
                        + "WHERE token_hash = ? AND usado = 0 AND expira_em > NOW() FOR UPDATE")) {
                    ps.setString(1, tokenHash);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            con.rollback();
                            return false;
                        }
                        idUsuario = rs.getInt("usuario_id");
                    }
                }
                try (PreparedStatement ps = con.prepareStatement(
                        "UPDATE usuario SET senhahash = ? WHERE id = ?")) {
                    ps.setString(1, novoHashSenha);
                    ps.setInt(2, idUsuario);
                    ps.executeUpdate();
                }
                // invalida este token e qualquer outro pendente do mesmo usuário
                try (PreparedStatement ps = con.prepareStatement(
                        "UPDATE token_recuperacao SET usado = 1 WHERE usuario_id = ? AND usado = 0")) {
                    ps.setInt(1, idUsuario);
                    ps.executeUpdate();
                }
                con.commit();
                return true;
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
