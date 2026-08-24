package com.allancleitonppma.sscagent.domain.model.entities;


import java.util.ArrayList;
import java.util.List;

public  class SalesLoad
{
    public Long LoadId;
    public List<Order> Orders = new ArrayList<>();
}
