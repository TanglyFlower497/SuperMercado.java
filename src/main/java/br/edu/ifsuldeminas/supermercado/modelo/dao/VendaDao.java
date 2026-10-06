package br.edu.ifsuldeminas.supermercado.modelo.dao;

import br.edu.ifsuldeminas.supermercado.entidade.Venda;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class VendaDao extends GenericoDAO<Venda> {

    public void salvar(Venda objVenda) {
        String sql = "INSERT INTO Venda(data, codCliente, codFuncionario, codFormaPagamento, total, desconto, observacao) VALUES(?,?,?,?,?,?,?)";
        save(sql, objVenda.getData(), objVenda.getCliente().getCodigoCliente(), objVenda.getFuncionario().getCodigoFuncionario(),
                objVenda.getFormaPagamento().getCodigoFormaPagamento(), objVenda.getTotal(), objVenda.getDesconto(), objVenda.getObservacao());
    }

    public void alterar(Venda objVenda) {
        String sql = "UPDATE Venda SET data=?, codCliente=?, codFuncionario=?, codFormaPagamento=?, total=?, desconto=?, observacao=? WHERE codVenda=?";
        save(sql, objVenda.getData(), objVenda.getCliente().getCodigoCliente(), objVenda.getFuncionario().getCodigoFuncionario(),
                objVenda.getFormaPagamento().getCodigoFormaPagamento(), objVenda.getTotal(), objVenda.getDesconto(), objVenda.getObservacao(), objVenda.getCodVenda());
    } 

    public void excluir(Venda objVenda) {
        String sql = "DELETE FROM Venda WHERE codVenda=?";
        save(sql, objVenda.getCodVenda());
    }

    public List<Venda> buscarTodasVendas() {
        String sql = "SELECT * FROM Venda";
        return buscarTodos(sql, new VendaRowMapper());
    }

    public Venda buscarVendaPorId(int id) {
        String sql = "SELECT * FROM Venda WHERE codVenda=?";
        return buscarPorId(sql, new VendaRowMapper(), id);
    }

    private static class VendaRowMapper implements RowMapper<Venda> {
        ClienteDao clienteDao = new ClienteDao();
        FuncionarioDao funcionarioDao = new FuncionarioDao();
        FormaPagamentoDao formaPagamentoDao = new FormaPagamentoDao();

        @Override
        public Venda mapRow(ResultSet rs) throws SQLException {
            Venda objVenda = new Venda();
            objVenda.setCodVenda(rs.getInt("codVenda"));
            objVenda.setData(rs.getDate("data"));
            objVenda.setTotal(rs.getDouble("total"));
            objVenda.setDesconto(rs.getDouble("desconto"));
            objVenda.setObservacao(rs.getString("observacao"));

            
            int codCliente = rs.getInt("codCliente");
            if (!rs.wasNull()) {
                objVenda.setCliente(clienteDao.buscarClientePorId(codCliente));
            }
            
            
            int codFuncionario = rs.getInt("codFuncionario");
            if (!rs.wasNull()) {
                objVenda.setFuncionario(funcionarioDao.buscarPorId(codFuncionario));
            }
            
            int codFormaPagamento = rs.getInt("codFormaPagamento");
            if (!rs.wasNull()) {
                objVenda.setFormaPagamento(formaPagamentoDao.buscarFormaPagamentoPorId(codFormaPagamento));
            }

            return objVenda;
        }
    }
}