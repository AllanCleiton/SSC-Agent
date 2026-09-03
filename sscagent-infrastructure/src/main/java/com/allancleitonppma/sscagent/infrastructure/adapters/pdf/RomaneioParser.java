package com.allancleitonppma.sscagent.infrastructure.adapters.pdf;

import com.allancleitonppma.sscagent.domain.model.entities.orderEntities.ItemOrder;
import com.allancleitonppma.sscagent.domain.model.entities.orderEntities.Order;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.allancleitonppma.sscagent.infrastructure.Utils.ExcelManipulation.DATE_FORMAT;

/*@ return: Extrai da string do PdfTextReader as informacoes concretas
* PDF
 │
 ├── Cabeçalho do relatório
 │
 ├── Pedido
 │    ├── Cliente
 │    ├── Endereço
 │    ├── Cidade
 │    ├── Estado
 │    ├── Rota
 │    ├── Sequência
 │    ├── Observação
 │    ├── Número do pedido
 │    ├── Data
 │    ├── Vendedor
 │    ├── Tipo negociação
 │    │
 │    └── Produtos[]
 │         ├── Código
 │         ├── Descrição
 │         ├── Qtd negociada
 │         ├── Valor unitário
 │         ├── Qtd corte
 │         ├── Qtd carregada
 │         └── Unidade
 │
 ├── Pedido
 │    └── ...
 │
 └── ...*/
public class RomaneioParser {
    private static final String ORDER_START = "Nome:";
    private static final float Y_TOLERANCE = 2.5f;
    private static final float X_TOLERANCE = 3.0f;

    private static final float PRODUCT_CODE_X = 14f;
    private static final float LOADED_QUANTITY_X = 440f;

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public List<List<PdfTextElement>> splitOrders(
            List<PdfTextElement> elements
    ) {

        List<List<PdfTextElement>> orders = new ArrayList<>();

        List<PdfTextElement> currentOrder = null;

        for (PdfTextElement element : elements) {

            if (isOrderStart(element)) {

                currentOrder = new ArrayList<>();

                orders.add(currentOrder);
            }

            if (currentOrder != null) {
                currentOrder.add(element);
            }
        }

        return orders;
    }



    private boolean isOrderStart(PdfTextElement element) {

        return ORDER_START.equalsIgnoreCase(
                element.text().trim()
        );
    }

    private PdfTextElement findValue(
            List<PdfTextElement> elements,
            String label,
            float labelX,
            float valueX
    ) {

        PdfTextElement labelElement = findLabel(
                elements,
                label,
                labelX
        );

        return elements.stream()
                .filter(element ->
                        Math.abs(element.y() - labelElement.y())
                                <= Y_TOLERANCE
                )
                .filter(element ->
                        Math.abs(element.x() - valueX)
                                <= X_TOLERANCE
                )
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Valor do campo '" + label +
                                        "' não encontrado."
                        )
                );
    }

    private PdfTextElement findLabel(
            List<PdfTextElement> elements,
            String label,
            float expectedX
    ) {

        return elements.stream()
                .filter(element ->
                        label.equalsIgnoreCase(
                                element.text().trim()
                        )
                )
                .filter(element ->
                        Math.abs(element.x() - expectedX)
                                <= X_TOLERANCE
                )
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Campo '" + label +
                                        "' não encontrado."
                        )
                );
    }

    private long parseCustomerId(String value) {

        String customerId = value
                .split(" - ", 2)[0]
                .trim();

        return Long.parseLong(customerId);
    }

    private Order parseOrderHeader(
            List<PdfTextElement> elements
    ) {

        Order order = new Order();

        order.customerId = parseCustomerId(
                findValue(
                        elements,
                        "Nome:",
                        25f,
                        59f
                ).text()
        );

        order.orderId = Long.parseLong(
                findValue(
                        elements,
                        "Pedido:",
                        311.85f,
                        335f
                ).text()
        );

        order.orderDate = LocalDate.parse(
                findValue(
                        elements,
                        "Data Pedido:",
                        296.54f,
                        335f
                ).text(),
                DATE_FORMAT
        );

        order.loadingInstruction =
                parseLoadingInstruction(elements);

        return order;
    }

    public List<Order> parse(List<PdfTextElement> elements) {

        List<Order> orders = new ArrayList<>();

        List<List<PdfTextElement>> orderBlocks =
                splitOrders(elements);

        for (List<PdfTextElement> orderBlock : orderBlocks) {

            Order order = parseOrderHeader(orderBlock);

            order.lines = parseItems(orderBlock);

            orders.add(order);
        }

        return orders;
    }


    private String parseLoadingInstruction(
            List<PdfTextElement> elements
    ) {

        PdfTextElement obsLabel = findLabel(
                elements,
                "Obs:",
                281.80f
        );

        PdfTextElement productsHeader = findLabel(
                elements,
                "Código",
                13.76f
        );

        List<PdfTextElement> observationElements =
                elements.stream()
                        .filter(element ->
                                element.y() >= obsLabel.y() - Y_TOLERANCE
                        )
                        .filter(element ->
                                element.y() < productsHeader.y()
                        )
                        .filter(element ->
                                Math.abs(element.x() - 296f)
                                        <= X_TOLERANCE
                        )
                        .sorted((a, b) -> {

                            int yCompare =
                                    Float.compare(a.y(), b.y());

                            if (yCompare != 0) {
                                return yCompare;
                            }

                            return Float.compare(a.x(), b.x());
                        })
                        .toList();

        if (observationElements.isEmpty()) {
            return null;
        }

        return observationElements.stream()
                .map(PdfTextElement::text)
                .reduce((a, b) -> a + " " + b)
                .orElse(null);
    }

    private List<ItemOrder> parseItems(
            List<PdfTextElement> elements
    ) {

        List<ItemOrder> items = new ArrayList<>();

        for (PdfTextElement codeElement : elements) {

            if (!isProductCode(codeElement)) {
                continue;
            }

            ItemOrder item = new ItemOrder();

            item.productCode =
                    Long.parseLong(codeElement.text());

            for (PdfTextElement quantityElement : elements) {

                if (Math.abs(
                        quantityElement.y() - codeElement.y()
                ) <= Y_TOLERANCE
                        && Math.abs(
                        quantityElement.x() - LOADED_QUANTITY_X
                ) <= X_TOLERANCE) {

                    item.quantity =
                            Double.parseDouble(
                                    quantityElement.text()
                                            .replace(",", ".")
                            );

                    break;
                }
            }

            items.add(item);
        }

        return items;
    }

    private boolean isProductCode(PdfTextElement element) {

        if (Math.abs(element.x() - PRODUCT_CODE_X) > X_TOLERANCE) {
            return false;
        }

        if (element.y() <= 126) {
            return false;
        }

        return element.text().matches("\\d+");
    }
}
