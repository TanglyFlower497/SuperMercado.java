/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.supermercado.entidade;

/**
 *
 * @author 12421698650
 */
public class Compra {
    private Integer codCompra;
    private String data;
    private String codFornecedor;
    private String total;
    private String observacao;

    public Integer getCodCompra() {
        return codCompra;
    }

    public void setCodCompra(Integer codCompra) {
        this.codCompra = codCompra;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCodFornecedor() {
        return codFornecedor;
    }

    public void setCodFornecedor(String codFornecedor) {
        this.codFornecedor = codFornecedor;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    
    
}
