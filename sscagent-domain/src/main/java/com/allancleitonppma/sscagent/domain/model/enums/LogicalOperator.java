package com.allancleitonppma.sscagent.domain.model.enums;

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
