package com.allancleitonppma.sscagent.domain.model.entities.productEntities;

import com.allancleitonppma.sscagent.domain.model.entities.stockEntities.Address;
import com.allancleitonppma.sscagent.domain.model.enums.StockAvailability;

import java.time.LocalDate;

public class StockBox {

        public String boxId;
        public String  palletId;
        public String productCode;
        public String productName;
        public Integer packagesPerBox;
        public Integer NetWeight;
        public LocalDate expirationDate;
        public LocalDate productionDate;
        public Integer daysToExpiry;
        public String sourceStatus;
        public Address address;
        public boolean isAvailable;

        public StockBox(
        ){
            this.isAvailable  = StockAvailability.Available.getValue();
        }


}
