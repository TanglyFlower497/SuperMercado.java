package br.edu.ifsuldeminas.supermercado.modelo.dao;

import br.edu.ifsuldeminas.supermercado.entidade.Produto;
import br.edu.ifsuldeminas.supermercado.entidade.Categoria;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProdutoDao extends GenericoDAO<Produto> {

    public void salvar(Produto obj) {
        String sql = "INSERT INTO produto(nome, codigoBarras, preco, custo, estoque, estoqueMinimo, validade, marca, codCategoria) VALUES(?,?,?,?,?,?,?,?,?)";

        save(sql,
                obj.getNomeProduto(),
                obj.getCodigoBarrasProduto(),
                obj.getPrecoProduto(),
                obj.getCustoProduto(),
                obj.getEstoqueProduto(),
                obj.getEstoqueMinimoProduto(),
                obj.getValidadeProduto(),
                obj.getMarcaProduto(),
                obj.getCategoria().getCodigoCategoria());
    }

    public void alterar(Produto obj) {
        String sql = "UPDATE produto SET nome=?, codigoBarras=?, preco=?, custo=?, estoque=?, estoqueMinimo=?, validade=?, marca=?, codCategoria=? WHERE codProduto=?";

        save(sql,
                obj.getNomeProduto(),
                obj.getCodigoBarrasProduto(),
                obj.getPrecoProduto(),
                obj.getCustoProduto(),
                obj.getEstoqueProduto(),
                obj.getEstoqueMinimoProduto(),
                obj.getValidadeProduto(),
                obj.getMarcaProduto(),
                obj.getCategoria().getCodigoCategoria(),
                obj.getCodigoProduto());
    }

    public void excluir(Produto obj) {
        String sql = "DELETE FROM produto WHERE codProduto=?";
        save(sql, obj.getCodigoProduto());
    }

    public List<Produto> buscarTodos() {
        String sql = "SELECT * FROM produto";
        return buscarTodos(sql, new ProdutoRowMapper());
    }

    public Produto buscarPorId(int id) {
        String sql = "SELECT * FROM produto WHERE codProduto=?";
        return buscarPorId(sql, new ProdutoRowMapper(), id);
    }

    private static class ProdutoRowMapper implements RowMapper<Produto> {

        CategoriaDao categoriaDao = new CategoriaDao();
        @Override
        public Produto mapRow(ResultSet rs) throws SQLException {

            Produto obj = new Produto();

            obj.setCodigoProduto(rs.getInt("codProduto"));
            obj.setNomeProduto(rs.getString("nome"));
            obj.setCodigoBarrasProduto(rs.getString("codigoBarras"));
            obj.setPrecoProduto(rs.getDouble("preco"));
            obj.setCustoProduto(rs.getDouble("custo"));
            obj.setEstoqueProduto(rs.getInt("estoque"));
            obj.setEstoqueMinimoProduto(rs.getInt("estoqueMinimo"));

            //  AQUI
            obj.setValidadeProduto(rs.getDate("validade"));

            obj.setMarcaProduto(rs.getString("marca"));

            int codCategoria = rs.getInt("codCategoria");

            if (!rs.wasNull()) {
                Categoria categoria = categoriaDao.buscarPorId(codCategoria);
                obj.setCategoria(categoria);
            }

            return obj;
        }
    }
}