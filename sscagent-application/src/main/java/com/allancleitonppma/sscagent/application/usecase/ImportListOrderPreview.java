package com.allancleitonppma.sscagent.application.usecase;

import com.allancleitonppma.sscagent.domain.model.entities.ItemOrder;
import com.allancleitonppma.sscagent.domain.model.entities.Order;
import com.allancleitonppma.sscagent.domain.model.entities.OrderPreview;
import com.allancleitonppma.sscagent.domain.model.entities.SalesLoad;

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
                orderPreview.setNeed(String.valueOf(itemOrder.quantity));
                orderPreview.setCondition(String.valueOf(itemOrder.condition));

                list.add(orderPreview);
            }
        }

        return list;
    }
}
