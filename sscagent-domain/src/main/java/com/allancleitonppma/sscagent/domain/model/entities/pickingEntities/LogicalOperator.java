package com.allancleitonppma.sscagent.domain.model.entities.pickingEntities;

public enum LogicalOperator {

    AND("&&"),
    OR("||");

    private final String symbol;

    LogicalOperator(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
