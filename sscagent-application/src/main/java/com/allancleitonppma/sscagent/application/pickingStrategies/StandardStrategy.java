package com.allancleitonppma.sscagent.application.pickingStrategies;


import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.InterpretedOrder;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.PickingMap;
import com.allancleitonppma.sscagent.domain.model.entities.productEntities.StockBox;
import com.allancleitonppma.sscagent.domain.model.entities.stockEntities.Pallet;

import java.util.List;

public class StandardStrategy implements PickingStrategy {

    /**
     * @param order
     * @param pallets
     * @return
     */
    @Override
    public PickingMap generated(InterpretedOrder order, List<Pallet> pallets, List<StockBox> boxes) {
        return null;
    }
}
