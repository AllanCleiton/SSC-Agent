package com.allancleitonppma.sscagent.domain.model.entities.orderEntities;

import java.util.ArrayList;
import java.util.List;

public class OrderPreview {

    private  String product;
    private double need;
    private  String condition;
    private String order;
    private  String instruction;

    private List<String> consolidateOrders = new ArrayList<>();

    public OrderPreview(
            String product,
            double need,
            String condition,
            String order,
            String instruction
    ) {
        this.product = product;
        this.need = need;
        this.condition = condition;
        this.order = order;
        this.instruction = instruction;
    }

    public OrderPreview(){}

    public String getProduct() {
        return product;
    }

    public double getNeed() {
        return need;
    }

    public String getCondition() {
        return condition;
    }

    public String getOrder() {
        return order;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setNeed(double need) {
        this.need = need;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public List<String> getConsolidateOrders() {
        return consolidateOrders;
    }

    public void setConsolidateOrders(List<String> consolidateOrders) {
        this.consolidateOrders = consolidateOrders;
    }
}
