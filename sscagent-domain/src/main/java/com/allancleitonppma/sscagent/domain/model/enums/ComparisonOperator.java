package com.allancleitonppma.sscagent.domain.model.enums;

public enum ComparisonOperator {
    GREATER_THAN(">"),
    LESS_THAN("<"),
    GREATER_OR_EQUAL(">="),
    LESS_OR_EQUAL("<="),
    EQUAL("="),
    NOT_EQUAL("!=");

    private final String symbol;

    ComparisonOperator(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
