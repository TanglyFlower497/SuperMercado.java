/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsuldeminas.supermercado.entidade;

/**
 *
 * @author vcart
 */

import java.sql.Date;

public class Venda {

    private Integer codVenda;
    private Date data;
    private Double total;
    private Double desconto;
    private String observacao;
    private Cliente cliente = new Cliente();
    private Funcionario funcionario = new Funcionario();
    private FormaPagamento formaPagamento = new FormaPagamento();

    public Integer getCodVenda() {
        return codVenda;
    }

    public void setCodVenda(Integer codVenda) {
        this.codVenda = codVenda;
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

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "codVenda=" + codVenda +
                ", data=" + data +
                ", total=" + total +
                ", desconto=" + desconto +
                ", observacao='" + observacao + '\'' +
                ", cliente=" + cliente +
                ", funcionario=" + funcionario +
                ", formaPagamento=" + formaPagamento +
                '}';
    }
}