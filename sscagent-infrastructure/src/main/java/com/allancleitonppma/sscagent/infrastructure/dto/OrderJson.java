package com.allancleitonppma.sscagent.infrastructure.dto;

import java.util.List;

public class OrderJson {

    public Long pedido;
    public Long cliente;
    public String estado;
    public String cidade;
    public Long rota;
    public Integer sequencia;
    public String dataPedido;
    public String instrucaoCarregamento;
    public List<OrderLineJson> produtos;

    public OrderJson(){

    }

    public Long getPedido() {
        return pedido;
    }

    public void setPedido(Long pedido) {
        this.pedido = pedido;
    }

    public Long getCliente() {
        return cliente;
    }

    public void setCliente(Long cliente) {
        this.cliente = cliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Long getRota() {
        return rota;
    }

    public void setRota(Long rota) {
        this.rota = rota;
    }

    public Integer getSequencia() {
        return sequencia;
    }

    public void setSequencia(Integer sequencia) {
        this.sequencia = sequencia;
    }

    public String getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(String dataPedido) {
        this.dataPedido = dataPedido;
    }

    public String getInstrucaoCarregamento() {
        return instrucaoCarregamento;
    }

    public void setInstrucaoCarregamento(String instrucaoCarregamento) {
        this.instrucaoCarregamento = instrucaoCarregamento;
    }

    public List<OrderLineJson> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<OrderLineJson> produtos) {
        this.produtos = produtos;
    }
}