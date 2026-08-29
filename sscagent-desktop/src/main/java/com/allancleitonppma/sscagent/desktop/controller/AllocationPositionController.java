package com.allancleitonppma.sscagent.desktop.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class AllocationPositionController {

    @FXML
    private VBox allocationProduct;

    public VBox getAllocationProduct() {
        return allocationProduct;
    }

    public void setAllocationProduct(VBox allocationProduct) {
        this.allocationProduct = allocationProduct;
    }
}
