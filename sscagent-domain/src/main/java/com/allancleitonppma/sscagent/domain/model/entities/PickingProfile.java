package com.allancleitonppma.sscagent.domain.model.entities;

import com.allancleitonppma.sscagent.domain.model.enums.QuantityUnit;

import java.util.ArrayList;
import java.util.List;

public  class PickingProfile
{
    public String name;
    public String description;
    public QuantityUnit defaultQuantityUnit = QuantityUnit.Kilograms;
    public boolean excludeWithoutAddress = true;
    public boolean excludeUnavailable = true;
    public boolean useExpirationOrder = true ;
    public boolean useAddressWeight = true;
    public List<RegexRule> regexRules = new ArrayList<>();
}