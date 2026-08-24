package com.allancleitonppma.sscagent.desktop.dto;

public class OrderDTO {
        String product;
        String need;
        String condition;
        String order;
        String instruction;


    public OrderDTO(String product, String need, String condition, String order, String instruction) {
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
}

