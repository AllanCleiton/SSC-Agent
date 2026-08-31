package com.allancleitonppma.sscagent.domain.model.entities.orderEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderPreview {
    private UUID id;
    private  String product;
    private double need;
    private  String condition;
    private String order;
    private  String instruction;

    private List<String> consolidateOrders = new ArrayList<>();

    public OrderPreview(
            UUID id,
            String product,
            double need,
            String condition,
            String order,
            String instruction
    ) {
        this.id = id;
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean updateNeed(String value) {

        double subtract = Double.parseDouble(value);

        if(subtract > this.need){
            return false;
        }

        this.need -= subtract;
        return true;
    }
}
