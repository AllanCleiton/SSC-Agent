package com.allancleitonppma.sscagent.infrastructure.adapters.pdf;

import com.allancleitonppma.sscagent.application.ports.SalesLoadReader;
import com.allancleitonppma.sscagent.domain.model.entities.orderEntities.Order;
import com.allancleitonppma.sscagent.domain.model.entities.orderEntities.SalesLoad;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PdfSalesLoadReader implements SalesLoadReader {
    /**
     * @param file
     * @return SalesLoad
     */
    @Override
    public SalesLoad read(Path file) throws IOException {
        PdfTextExtractor extractor = new PdfTextExtractor();

        RomaneioParser parser = new RomaneioParser();

        List<PdfTextElement> elements =
                extractor.extract(file.toFile());

        List<Order> orders =
                parser.parse(elements);

        SalesLoad salesLoad = new SalesLoad();

        salesLoad.Orders = new ArrayList<>(orders);
        salesLoad.LoadId = extractLoadId(file);

        return salesLoad;


        /*System.out.println(
                "Pedidos encontrados: " + orders.size()
        );

        for (Order order : orders) {

            System.out.println("-----------------------------");

            System.out.println("Cliente: " + order.customerId);
            System.out.println("Pedido: " + order.orderId);
            System.out.println("Data: " + order.orderDate);
            System.out.println(
                    "Instrução: " + order.loadingInstruction
            );

            for (ItemOrder item : order.lines) {

                System.out.println(
                        "Produto: " + item.productCode
                                + " | Quantidade: "
                                + item.quantity
                );
            }
        }*/

    }

    private Long extractLoadId(Path file) {

        String fileName = file.getFileName().toString();

        String loadId = fileName.replaceFirst(
                "\\.pdf$",
                ""
        );

        return Long.parseLong(loadId);
    }
}
