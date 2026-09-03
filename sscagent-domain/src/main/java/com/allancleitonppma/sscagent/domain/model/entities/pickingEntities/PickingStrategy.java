package com.allancleitonppma.sscagent.domain.model.entities.pickingEntities;

public interface PickingStrategy {
    PickingMap generated(InterpretedOrder order);
}
