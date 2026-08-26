package com.allancleitonppma.sscagent.domain.model.enums;

public enum StockAvailability {
    Available(true),
    Unavailable(false),
    Consumed(false),
    Blocked(false);

    private StockAvailability(boolean value){
    }


    public boolean getValue(){
        return this.getValue();
    }
}
