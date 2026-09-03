package com.allancleitonppma.sscagent.infrastructure.adapters.json;

import com.allancleitonppma.sscagent.application.ports.SalesLoadReader;
import com.allancleitonppma.sscagent.infrastructure.dto.OrderJson;
import com.allancleitonppma.sscagent.infrastructure.dto.OrderLineJson;
import com.allancleitonppma.sscagent.infrastructure.dto.SalesLoadJson;
import tools.jackson.databind.ObjectMapper;

import com.allancleitonppma.sscagent.domain.model.entities.orderEntities.Order;
import com.allancleitonppma.sscagent.domain.model.entities.orderEntities.ItemOrder;
import com.allancleitonppma.sscagent.domain.model.entities.orderEntities.SalesLoad;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JsonSalesLoadReader implements SalesLoadReader {

    private final ObjectMapper objectMapper;


    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public JsonSalesLoadReader() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public SalesLoad read(Path file) {

        SalesLoadJson json = objectMapper.readValue(file.toFile(), SalesLoadJson.class);

        return map(json);

    }


    private SalesLoad map(SalesLoadJson json) {

        SalesLoad salesLoad = new SalesLoad();

        salesLoad.LoadId = json.cargaVenda;

        if (json.pedidos == null) {
            return salesLoad;
        }

        for (OrderJson orderJson : json.pedidos) {

            Order order = mapOrder(orderJson);

            salesLoad.Orders.add(order);
        }

        return salesLoad;
    }

    private Order mapOrder(OrderJson json) {

        Order order = new Order();

        order.orderId = json.pedido;
        order.customerId = json.cliente;
        order.state = json.estado;
        order.city = json.cidade;
        order.route = json.rota;
        order.sequence = json.sequencia;
        order.loadingInstruction = json.instrucaoCarregamento;

        if (json.dataPedido != null) {
            order.orderDate =
                    LocalDate.parse(json.dataPedido, DATE_FORMAT);
        }

        if (json.produtos != null) {

            for (OrderLineJson lineJson : json.produtos) {

                ItemOrder line = mapOrderLine(lineJson);

                order.lines.add(line);
            }
        }

        return order;
    }

    private ItemOrder mapOrderLine(OrderLineJson json) {

        ItemOrder line = new ItemOrder();

        line.productCode = json.codProduto;
        line.quantity = json.qtde;
        line.condition = json.condicao;

        return line;
    }
}