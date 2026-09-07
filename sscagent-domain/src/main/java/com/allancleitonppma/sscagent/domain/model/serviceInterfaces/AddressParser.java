package com.allancleitonppma.sscagent.domain.model.serviceInterfaces;

import com.allancleitonppma.sscagent.domain.model.entities.stockEntities.Address;

public interface AddressParser {

    Address parse(String original);
}