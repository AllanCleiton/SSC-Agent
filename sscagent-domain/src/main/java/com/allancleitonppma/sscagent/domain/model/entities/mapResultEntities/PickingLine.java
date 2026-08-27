package com.allancleitonppma.sscagent.domain.model.entities.mapResultEntities;

import com.allancleitonppma.sscagent.domain.model.entities.stockEntities.Address;
import com.allancleitonppma.sscagent.domain.model.enums.QuantityUnit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public  class PickingLine
{
    public int sequence;
    public long orderId;
    public String orderReference = "";
    public int productCode;
    public String productName = "";
    public Double quantity;
    public QuantityUnit quantityUnit;
    public int boxCount;
    public Double packagesPerBox;
    public String palletId = "";
    public String addressSummary = "";
    public String operationalPositionSummary = "";
    public String expirationSummary = "";
    public String boxDescription = "";
    public Address address;
    public LocalDate expirationDate;
    public int daysToExpiry;
    public String InstructionText = "";
    public String Status = "Planejado";
    public List<String> PhysicalBoxIds = new ArrayList<>();
}