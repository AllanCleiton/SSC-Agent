package com.allancleitonppma.sscagent.application.usecase;

import com.allancleitonppma.sscagent.domain.model.entities.orderEntities.ItemOrder;
import com.allancleitonppma.sscagent.domain.model.entities.orderEntities.Order;
import com.allancleitonppma.sscagent.domain.model.entities.orderEntities.OrderPreview;
import com.allancleitonppma.sscagent.domain.model.entities.orderEntities.SalesLoad;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConsolidateSalesLoad {
    static List<OrderPreview> previews = new ArrayList<>();

    private static void  preConsolidate(Path path, ImportSalesLoadUseCase salesLoadUseCase) {

        SalesLoad salesLoad = salesLoadUseCase.execute(path);


        for (Order order : salesLoad.Orders) {

            for (ItemOrder itemOrder : order.lines) {

                OrderPreview orderPreview = new OrderPreview();

                orderPreview.setOrder(String.valueOf(order.orderId));
                orderPreview.setInstruction(String.valueOf(order.loadingInstruction));
                orderPreview.setProduct(String.valueOf(itemOrder.productCode));
                orderPreview.setNeed(itemOrder.quantity);
                orderPreview.setCondition(String.valueOf(itemOrder.condition));

                previews.add(orderPreview);
            }
        }

    }


    public static List<OrderPreview> consolidate(Path path, ImportSalesLoadUseCase salesLoadUseCase) {
        preConsolidate(path, salesLoadUseCase);

        Map<ConsolidationKey, OrderPreview> consolidated = new LinkedHashMap<>();

        for (OrderPreview preview : previews) {

            ConsolidationKey key = new ConsolidationKey(
                    preview.getProduct(),
                    preview.getCondition(),
                    preview.getInstruction()
            );

            OrderPreview result = consolidated.get(key);

            if (result == null) {

                result = new OrderPreview(
                        preview.getProduct(),
                        preview.getNeed(),
                        preview.getCondition(),
                        preview.getOrder(),
                        preview.getInstruction()
                );

                result.getConsolidateOrders().add(preview.getOrder());

                consolidated.put(key, result);

            } else {

                result.setNeed(
                        result.getNeed() + preview.getNeed()
                );

                if (!result.getConsolidateOrders()
                        .contains(preview.getOrder())) {

                    result.getConsolidateOrders()
                            .add(preview.getOrder());
                }
            }
        }

        return new ArrayList<>(consolidated.values());
    }

    private record ConsolidationKey(
            String product,
            String condition,
            String instruction
    ) {}
}
