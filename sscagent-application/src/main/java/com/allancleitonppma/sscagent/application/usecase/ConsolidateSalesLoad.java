package com.allancleitonppma.sscagent.application.usecase;

import com.allancleitonppma.sscagent.domain.model.entities.orderEntities.ItemOrder;
import com.allancleitonppma.sscagent.domain.model.entities.orderEntities.Order;
import com.allancleitonppma.sscagent.domain.model.entities.orderEntities.OrderPreview;
import com.allancleitonppma.sscagent.domain.model.entities.orderEntities.SalesLoad;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class ConsolidateSalesLoad {
    static List<OrderPreview> previews = new ArrayList<>();

    private static void preConsolidat(Path path, ImportSalesLoadUseCase ImportSalesLoad) throws IOException {

        //RECEBE A ORDEM DE CARGA IMPORTADA PELO ImportSalesLoadUser Case
        SalesLoad salesLoad = ImportSalesLoad.execute(path);

        //ITERA SOBRE AS ORDENS DE CARGA salesLoad
        for (Order order : salesLoad.Orders) {
            //PARA CATA OrderItem da order.lines faca.
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


    public static List<OrderPreview> consolidate(Path path, ImportSalesLoadUseCase salesLoadUseCase, List<OrderPreview> ordes) throws IOException {
        //Aqui ele chama o metodo preConsolidat, que transformou cada Order order em OrderPreview previews
        if(salesLoadUseCase == null && !(ordes.isEmpty())){
            previews.clear();
            previews.addAll(ordes);
        }else if(salesLoadUseCase != null){
            preConsolidat(path, salesLoadUseCase);
        }

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
                        UUID.randomUUID(),
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

        previews.clear();
        return consolidated.values().stream().toList();
    }

    private record ConsolidationKey(
            String product,
            String condition,
            String instruction
    ) {}
}
