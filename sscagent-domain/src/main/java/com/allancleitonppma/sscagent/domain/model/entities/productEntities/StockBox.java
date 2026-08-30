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
        public Integer packagesPerBox;
        public Integer NetWeight;
        public LocalDate expirationDate;
        public LocalDate productionDate;
        public Integer daysToExpiry;
        public String sourceStatus;
        public boolean isAvailable;

        public StockBox(
        ){
            this.isAvailable  = StockAvailability.Available.getValue();
        }


}
