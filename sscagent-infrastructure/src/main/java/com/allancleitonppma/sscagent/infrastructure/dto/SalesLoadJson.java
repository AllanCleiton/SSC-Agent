package com.allancleitonppma.sscagent.infrastructure.dto;

import java.util.List;

public class SalesLoadJson {

    public Long cargaVenda;

    public List<OrderJson> pedidos;

    public SalesLoadJson(Long cargaVenda, List<OrderJson> pedidos) {
        this.cargaVenda = cargaVenda;
        this.pedidos = pedidos;
    }

    public SalesLoadJson() {
    }

    public Long getCargaVenda() {
        return cargaVenda;
    }

    public void setCargaVenda(Long cargaVenda) {
        this.cargaVenda = cargaVenda;
    }

    public List<OrderJson> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<OrderJson> pedidos) {
        this.pedidos = pedidos;
    }
}
