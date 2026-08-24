package com.allancleitonppma.sscagent.domain.model.entities;

import com.allancleitonppma.sscagent.domain.model.enums.StockAvailability;

import java.time.LocalDate;

public class StockBox {

        String boxId;
        String  palletId;
        Integer productCode;
        String productName;
        Double packagesPerBox;
        Double netWeight;
        Double grossWeight;
        LocalDate expirationDate;
        LocalDate productionDate;
        Integer daysToExpiry;
        String lot;
        String sourceStatus;
        Address address;
        StockAvailability availability = StockAvailability.Available;
        boolean isAvailable = availability == StockAvailability.Available? true : false;

}
