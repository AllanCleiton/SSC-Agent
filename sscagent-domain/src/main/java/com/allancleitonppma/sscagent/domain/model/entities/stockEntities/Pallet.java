package com.allancleitonppma.sscagent.domain.model.entities.stockEntities;

import java.util.ArrayList;
import java.util.List;

public class Pallet
{
    String palletID;
    String ProductDescription;

    String status;
    Address address;
    int expectedBoxCount;
    List<String> boxIds = new ArrayList<>();
}