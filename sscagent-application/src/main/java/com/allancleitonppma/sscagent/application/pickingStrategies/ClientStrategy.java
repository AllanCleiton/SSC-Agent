package com.allancleitonppma.sscagent.application.pickingStrategies;


import com.allancleitonppma.sscagent.application.usecase.ImportPallet;
import com.allancleitonppma.sscagent.application.usecase.ImportStockBox;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.InterpretedOrder;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.PickingMap;
import java.io.IOException;
import java.util.List;

public class ClientStrategy implements PickingStrategy {

    @Override
    public PickingMap generated(InterpretedOrder order, ImportPallet importPallet, ImportStockBox importStockBox) throws IOException {
        return null;
    }
}
