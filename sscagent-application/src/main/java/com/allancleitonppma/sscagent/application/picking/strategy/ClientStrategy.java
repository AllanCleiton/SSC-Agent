package com.allancleitonppma.sscagent.application.picking.strategy;


import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.InterpretedOrder;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.PickingMap;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.PickingStrategy;

public class ClientStrategy implements PickingStrategy {
    /**
     * @param order
     * @return
     */
    @Override
    public PickingMap generated(InterpretedOrder order) {
        return null;
    }
}
