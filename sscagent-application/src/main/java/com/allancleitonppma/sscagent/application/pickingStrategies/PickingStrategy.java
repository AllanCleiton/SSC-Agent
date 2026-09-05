package com.allancleitonppma.sscagent.application.pickingStrategies;

import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.InterpretedOrder;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.PickingMap;
import com.allancleitonppma.sscagent.domain.model.entities.stockEntities.Pallet;

import java.util.List;

public interface PickingStrategy {
    PickingMap generated(InterpretedOrder order, List<Pallet> pallets);
}
