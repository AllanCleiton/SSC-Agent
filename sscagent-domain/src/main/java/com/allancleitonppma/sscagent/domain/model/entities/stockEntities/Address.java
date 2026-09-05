package com.allancleitonppma.sscagent.domain.model.entities.stockEntities;


import java.util.Arrays;
import java.util.Objects;

public class Address{
    String original;
    String camera;
    String street;
    String position;


    public Address(String original, String camera, String street, String position) {
        this.original = original;
        this.camera = camera;
        this.street = street;
        this.position = position;

    }

    public String DisplayName = ((original != null) ? String.join( " - ", (CharSequence) Arrays.stream(new String[] { camera, street, position }).filter(Objects::nonNull)) : null);

}

