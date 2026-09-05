package com.allancleitonppma.sscagent.domain.model.entities.productEntities;

import com.allancleitonppma.sscagent.domain.model.entities.stockEntities.Address;
import com.allancleitonppma.sscagent.domain.model.enums.StockAvailability;

import java.time.LocalDate;

public class StockBox {
        public Address address;
        public String  palletId;
        public String productId;
        public String productCode;
        public Long SankhyaId;
        public String productName;
        public Integer packages;
        public Double NetWeight;
        public LocalDate expirationDate;
        public LocalDate productionDate;
        public Integer daysToExpiry;
        public String sourceStatus;
        public boolean isAvailable;

        public StockBox(
        ){
            this.isAvailable  = StockAvailability.Available.getValue();
        }


    public Address getAddress() {
        return address;
    }

    public String getPalletId() {
        return palletId;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public Long getSankhyaId() {
        return SankhyaId;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getPackages() {
        return packages;
    }

    public Double getNetWeight() {
        return NetWeight;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public Integer getDaysToExpiry() {
        return daysToExpiry;
    }

    public String getSourceStatus() {
        return sourceStatus;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setDaysToExpiry(Integer daysToExpiry) {
        this.daysToExpiry = daysToExpiry;
    }
}
