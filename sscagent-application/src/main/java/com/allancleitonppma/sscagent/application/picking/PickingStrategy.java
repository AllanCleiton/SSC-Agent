package com.allancleitonppma.sscagent.application.picking;

public interface PickingStrategy {
    PickingMap generate(PickingContext context);
}
