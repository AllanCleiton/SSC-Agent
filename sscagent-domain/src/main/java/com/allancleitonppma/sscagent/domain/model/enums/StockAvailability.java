package com.allancleitonppma.sscagent.domain.model.enums;

public enum StockAvailability {

    Available(true),
    Unavailable(false),
    Consumed(false),
    Blocked(false);

    private final boolean value;

    StockAvailability(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }
}