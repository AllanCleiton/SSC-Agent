package com.allancleitonppma.sscagent.application.picking.strategy;

import com.allancleitonppma.sscagent.application.picking.PickingMap;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.InterpretedOrder;

public class UserProfileStrategy implements PickingStrategy{
    /**
     * @param order
     * @return
     */
    @Override
    public PickingMap generated(InterpretedOrder order) {
        return null;
    }
}
