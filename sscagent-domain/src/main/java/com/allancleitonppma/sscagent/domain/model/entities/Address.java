package com.allancleitonppma.sscagent.domain.model.entities;


import java.util.Arrays;
import java.util.Objects;

public class Address{
    String original;
    String camera;
    String street;
    String position;
    Double weight;

    public Address(String original, String camera, String street, String position, Double weight) {
        this.original = original;
        this.camera = camera;
        this.street = street;
        this.position = position;
        this.weight = weight;
    }

    public String DisplayName = ((original != null) ? String.join( " - ", (CharSequence) Arrays.stream(new String[] { camera, street, position }).filter(Objects::nonNull)) : null);

}

