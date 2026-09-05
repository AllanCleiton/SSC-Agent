package com.allancleitonppma.sscagent.domain.model.entities.stockEntities;

import java.util.ArrayList;
import java.util.List;

public class Pallet
{
    String palletID;
    String ProductDescription;
    String productCode;
    String SankhyaId;
    String status;
    Address address;
    int expectedBoxCount;
    List<String> boxIds = new ArrayList<>();

    public Pallet(String palletID, String productDescription, String productCode, String sankhyaId, String status, Address address, int expectedBoxCount) {
        this.palletID = palletID;
        ProductDescription = productDescription;
        this.productCode = productCode;
        SankhyaId = sankhyaId;
        this.status = status;
        this.address = address;
        this.expectedBoxCount = expectedBoxCount;
    }
    public Pallet(){

    }

    public String getPalletID() {
        return palletID;
    }

    public void setPalletID(String palletID) {
        this.palletID = palletID;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getSankhyaId() {
        return SankhyaId;
    }

    public void setSankhyaId(String sankhyaId) {
        SankhyaId = sankhyaId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getExpectedBoxCount() {
        return expectedBoxCount;
    }

    public void setExpectedBoxCount(int expectedBoxCount) {
        this.expectedBoxCount = expectedBoxCount;
    }

    public List<String> getBoxIds() {
        return boxIds;
    }

}