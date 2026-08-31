package com.allancleitonppma.sscagent.desktop.dto;

import java.util.UUID;

public class OrderDTO {
        UUID id;
        String product;
        String need;
        String condition;
        String order;
        String instruction;


    public OrderDTO(UUID id,String product, String need, String condition, String order, String instruction) {
        this.id = id;
        this.product = product;
        this.need = need;
        this.condition = condition;
        this.order = order;
        this.instruction = instruction;
    }

    public OrderDTO(){};

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getNeed() {
        return need;
    }

    public void setNeed(String need) {
        this.need = need;
    }

    public boolean updateNeed(String value) {
        double oldValue = Double.parseDouble(this.need);
        double subtract = Double.parseDouble(value);

        if(subtract < oldValue){
            return false;
        }


        this.need = String.valueOf(oldValue-subtract);
        return true;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}

