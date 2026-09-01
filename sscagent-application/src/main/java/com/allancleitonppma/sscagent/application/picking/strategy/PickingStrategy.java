package com.allancleitonppma.sscagent.application.picking.strategy;

import com.allancleitonppma.sscagent.application.picking.PickingMap;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.InterpretedOrder;

public interface PickingStrategy {
    PickingMap generated(InterpretedOrder order);
}
