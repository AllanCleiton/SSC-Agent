package com.allancleitonppma.sscagent.domain.model.entities.stockEntities;


import java.util.Arrays;
import java.util.Objects;

public class Address{
    String original;
    String camera;
    String street;
    String position;
    Integer peso;

    public Address(String original, String camera, String street, String position, Integer peso) {
        this.original = original;
        this.camera = camera;
        this.street = street;
        this.position = position;
        this.peso = peso;

    }

    public String DisplayName = ((original != null) ? String.join( " - ", (CharSequence) Arrays.stream(new String[] { camera, street, position }).filter(Objects::nonNull)) : null);

}

