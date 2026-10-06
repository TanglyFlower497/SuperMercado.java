package br.edu.ifsuldeminas.supermercado.modelo.dao;

import br.edu.ifsuldeminas.supermercado.entidade.Compra;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author 12421698650
 */
public class CompraDao extends GenericoDAO<Compra> {

    public void salvar(Compra objCompra) {
        String sql = "INSERT INTO Compra(data, codFornecedor, total, observacao) VALUES(?,?,?,?)";
        save(sql, objCompra.getData(), objCompra.getFornecedor().getCodigoFornecedor(),
                objCompra.getTotal(), objCompra.getObservacao());
    }

    public void alterar(Compra objCompra) {
        String sql = "UPDATE Compra SET data=?, codFornecedor=?, total=?, observacao=? WHERE codCompra=?";
        save(sql, objCompra.getData(), objCompra.getFornecedor().getCodigoFornecedor(),
                objCompra.getTotal(), objCompra.getObservacao(), objCompra.getCodCompra());
    } 

    public void excluir(Compra objCompra) {
        String sql = "DELETE FROM Compra WHERE codCompra=?";
        save(sql, objCompra.getCodCompra());
    }

    public List<Compra> buscarTodasCompras() {
        String sql = "SELECT * FROM Compra";
        return buscarTodos(sql, new CompraRowMapper());
    }

    public Compra buscarCompraPorId(int id) {
        String sql = "SELECT * FROM Compra WHERE codCompra=?";
        return buscarPorId(sql, new CompraRowMapper(), id);
    }

    private static class CompraRowMapper implements RowMapper<Compra> {
        FornecedorDao fornecedorDao = new FornecedorDao();

        @Override
        public Compra mapRow(ResultSet rs) throws SQLException {
            Compra objCompra = new Compra();
            objCompra.setCodCompra(rs.getInt("codCompra"));
            objCompra.setData(rs.getDate("data"));
            objCompra.setTotal(rs.getDouble("total"));
            objCompra.setObservacao(rs.getString("observacao"));

            int codFornecedor = rs.getInt("codFornecedor");
            if (!rs.wasNull()) {
                objCompra.setFornecedor(fornecedorDao.buscarPorId(codFornecedor));
            }

            return objCompra;
        }
    }
}