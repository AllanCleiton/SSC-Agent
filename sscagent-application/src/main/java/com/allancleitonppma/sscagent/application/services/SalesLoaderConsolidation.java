package com.allancleitonppma.sscagent.application.services;

import com.allancleitonppma.sscagent.domain.model.entities.ConsolidatedOrder;
import com.allancleitonppma.sscagent.domain.model.entities.Order;
import com.allancleitonppma.sscagent.domain.model.entities.ItemOrder;
import com.allancleitonppma.sscagent.domain.model.entities.SalesLoad;

import java.util.ArrayList;
import java.util.List;

public class SalesLoaderConsolidation {
    private final SalesLoad salesLoad;

    public SalesLoaderConsolidation(SalesLoad salesLoad){
        this.salesLoad = salesLoad;
    }
    private List<ConsolidatedOrder> getListOrderDto() {

        List<ConsolidatedOrder> list = new ArrayList<>();

        for (Order order : salesLoad.Orders) {

            for (ItemOrder itemOrder : order.lines) {

                ConsolidatedOrder consolidatedOrder = new ConsolidatedOrder();

                consolidatedOrder.setOrder(String.valueOf(order.orderId));
                consolidatedOrder.setInstruction(String.valueOf(order.loadingInstruction));
                consolidatedOrder.setProduct(String.valueOf(itemOrder.productCode));
                consolidatedOrder.setNeed(String.valueOf(itemOrder.quantity));
                consolidatedOrder.setCondition(String.valueOf(itemOrder.condition));

                list.add(consolidatedOrder);
            }
        }

        return list;
    }




}
