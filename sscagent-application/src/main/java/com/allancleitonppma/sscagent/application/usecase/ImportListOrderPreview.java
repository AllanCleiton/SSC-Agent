package com.allancleitonppma.sscagent.application.usecase;

import com.allancleitonppma.sscagent.domain.model.entities.orderEntities.ItemOrder;
import com.allancleitonppma.sscagent.domain.model.entities.orderEntities.Order;
import com.allancleitonppma.sscagent.domain.model.entities.orderEntities.OrderPreview;
import com.allancleitonppma.sscagent.domain.model.entities.orderEntities.SalesLoad;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ImportListOrderPreview {

    public static List<OrderPreview> getListOrderDto(Path path, ImportSalesLoadUseCase salesLoadUseCase) {


        SalesLoad salesLoad = salesLoadUseCase.execute(path);

        List<OrderPreview> list = new ArrayList<>();

        for (Order order : salesLoad.Orders) {

            for (ItemOrder itemOrder : order.lines) {

                OrderPreview orderPreview = new OrderPreview();

                orderPreview.setOrder(String.valueOf(order.orderId));
                orderPreview.setInstruction(String.valueOf(order.loadingInstruction));
                orderPreview.setProduct(String.valueOf(itemOrder.productCode));
                orderPreview.setNeed(itemOrder.quantity);
                orderPreview.setCondition(String.valueOf(itemOrder.condition));

                list.add(orderPreview);
            }
        }

        return list;
    }
}
