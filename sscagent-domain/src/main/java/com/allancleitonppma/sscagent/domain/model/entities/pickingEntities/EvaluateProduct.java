package com.allancleitonppma.sscagent.domain.model.entities.pickingEntities;

import com.allancleitonppma.sscagent.domain.model.entities.productEntities.StockBox;
import com.allancleitonppma.sscagent.domain.model.entities.stockEntities.Pallet;

public interface EvaluateProduct {
    Boolean evaluate(StockBox box, Pallet pallet);
}
