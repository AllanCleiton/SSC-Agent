package com.allancleitonppma.sscagent.domain.picking;

public interface PickingStrategy {
    PickingMap generate(PickingContext context);
}
