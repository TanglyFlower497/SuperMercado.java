package br.edu.ifsuldeminas.supermercado.entidade;
import java.sql.Date;

/**
 *
 * @author vcart
 */
public class Produto {
    private Integer codigoProduto;
    private String nomeProduto;
    private String codigoBarrasProduto;
    private Double precoProduto;
    private Double custoProduto;
    private Integer estoqueProduto;
    private Integer estoqueMinimoProduto;
    private Date validadeProduto;
    private String marcaProduto;
    private Categoria categoria = new Categoria();

    public Integer getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(Integer codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getCodigoBarrasProduto() {
        return codigoBarrasProduto;
    }

    public void setCodigoBarrasProduto(String codigoBarrasProduto) {
        this.codigoBarrasProduto = codigoBarrasProduto;
    }

    public Double getPrecoProduto() {
        return precoProduto;
    }

    public void setPrecoProduto(Double precoProduto) {
        this.precoProduto = precoProduto;
    }

    public Double getCustoProduto() {
        return custoProduto;
    }

    public void setCustoProduto(Double custoProduto) {
        this.custoProduto = custoProduto;
    }

    public Integer getEstoqueProduto() {
        return estoqueProduto;
    }

    public void setEstoqueProduto(Integer estoqueProduto) {
        this.estoqueProduto = estoqueProduto;
    }

    public Integer getEstoqueMinimoProduto() {
        return estoqueMinimoProduto;
    }

    public void setEstoqueMinimoProduto(Integer estoqueMinimoProduto) {
        this.estoqueMinimoProduto = estoqueMinimoProduto;
    }

    public Date getValidadeProduto() {
        return validadeProduto;
    }

    public void setValidadeProduto(Date validadeProduto) {
        this.validadeProduto = validadeProduto;
    }

    public String getMarcaProduto() {
        return marcaProduto;
    }

    public void setMarcaProduto(String marcaProduto) {
        this.marcaProduto = marcaProduto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}