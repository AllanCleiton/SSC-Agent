package com.allancleitonppma.sscagent.application.pickingStrategies;

import com.allancleitonppma.sscagent.application.usecase.ImportPallet;
import com.allancleitonppma.sscagent.application.usecase.ImportStockBox;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.InterpretedOrder;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.PickingMap;
import com.allancleitonppma.sscagent.domain.model.entities.productEntities.StockBox;
import com.allancleitonppma.sscagent.domain.model.entities.stockEntities.Pallet;

import java.io.IOException;
import java.util.List;

public interface PickingStrategy {
    PickingMap generated(InterpretedOrder order, ImportPallet importPallet, ImportStockBox importStockBox) throws IOException;
}
