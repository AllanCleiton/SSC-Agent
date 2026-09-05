package com.allancleitonppma.sscagent.domain.model.enums;

public enum Rigor {
    NENHUM(0),
    BAIXA(1),
    MEDIA(2),
    ALTA(3),
    CRITICA(4);

    final int rigor;

    private Rigor(int rigor){
        this.rigor = rigor;
    }
    @Override
    public String toString(){
        return String.valueOf(this.rigor);
    }
}
