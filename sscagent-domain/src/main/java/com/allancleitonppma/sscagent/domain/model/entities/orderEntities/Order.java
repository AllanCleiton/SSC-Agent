package com.allancleitonppma.sscagent.domain.model.entities.orderEntities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order
{
    public long orderId;
    public long customerId;
    public String state;
    public String city;
    public long route;
    public int  sequence;
    public LocalDate orderDate;
    public String loadingInstruction;
    public List<ItemOrder> lines = new ArrayList<>();
}
