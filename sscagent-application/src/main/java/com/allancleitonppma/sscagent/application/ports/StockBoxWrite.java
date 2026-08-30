package com.allancleitonppma.sscagent.application.ports;

import com.allancleitonppma.sscagent.domain.model.entities.productEntities.StockBox;

public interface StockBoxWrite {
    Boolean StockBoxUpDate(String productId, StockBox box);
}
