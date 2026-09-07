package com.allancleitonppma.sscagent.domain.model.entities.stockEntities;



public class Address implements Comparable<Address> {

    private  String original;
    private  String zone;
    private  String street;
    private  String module;
    private  String level;
    private  Integer peso;

    public Address() {}


    public Address(String original, String zone, String street, String module, String level, Integer peso) {
        this.original = original;
        this.zone = zone;
        this.street = street;
        this.module = module;
        this.level = level;
        this.peso = peso;
    }

    /**
     * Ordena os endereços pelo peso da posição física,
     * priorizando posições de menor dificuldade de coleta.
     */
    @Override
    public int compareTo(Address other) {
        return Integer.compare(this.peso, other.peso);
    }

    public String getOriginal() {
        return original;
    }

    public String getZone() {
        return zone;
    }

    public String getStreet() {
        return street;
    }

    public String getModule() {
        return module;
    }

    public String getLevel() {
        return level;
    }

    public Integer getPeso() {
        return peso;
    }

    @Override
    public String toString() {
        return "Address{" +
                "original='" + original + '\'' +
                ", zone='" + zone + '\'' +
                ", street='" + street + '\'' +
                ", module='" + module + '\'' +
                ", level='" + level + '\'' +
                ", peso=" + peso +
                '}';
    }
}

