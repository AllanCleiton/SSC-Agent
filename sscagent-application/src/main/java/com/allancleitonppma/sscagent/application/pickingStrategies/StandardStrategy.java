package com.allancleitonppma.sscagent.application.pickingStrategies;


import com.allancleitonppma.sscagent.application.usecase.ImportPallet;
import com.allancleitonppma.sscagent.application.usecase.ImportStockBox;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.InterpretedOrder;
import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.PickingMap;
import java.io.IOException;
public class StandardStrategy implements PickingStrategy {


    /**
     * @param order
     * @param importPallet
     * @param importStockBox
     * @return
     * @throws IOException
     */
    @Override
    public PickingMap generated(InterpretedOrder order, ImportPallet importPallet, ImportStockBox importStockBox) throws IOException {
        return null;
    }
}
