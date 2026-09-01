package com.allancleitonppma.sscagent.domain.model.entities.productEntities;

import java.util.ArrayList;
import java.util.List;

public class ProductCategory {
    private String categoryName;
    private Integer ShelfLife;
    private final List<String> productsContents = new ArrayList<>();

    public ProductCategory(String categoryName, Integer shelfLife) {
        this.categoryName = categoryName;
        ShelfLife = shelfLife;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getShelfLife() {
        return ShelfLife;
    }

    public void setShelfLife(Integer shelfLife) {
        ShelfLife = shelfLife;
    }

    public List<String> getProductsContents() {
        return productsContents;
    }
}
