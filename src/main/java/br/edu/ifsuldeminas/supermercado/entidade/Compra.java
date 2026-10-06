package br.edu.ifsuldeminas.supermercado.entidade;

import java.sql.Date;

public class Compra {

    private Integer codCompra;
    private Date data;
    private Double total;
    private String observacao;
    private Fornecedor fornecedor = new Fornecedor();

    public Integer getCodCompra() {
        return codCompra;
    }

    public void setCodCompra(Integer codCompra) {
        this.codCompra = codCompra;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public String toString() {
        return "Compra{" +
                "codCompra=" + codCompra +
                ", data=" + data +
                ", total=" + total +
                ", observacao=" + observacao +
                ", fornecedor=" + fornecedor +
                '}';
    }
}