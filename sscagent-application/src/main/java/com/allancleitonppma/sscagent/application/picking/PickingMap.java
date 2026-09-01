package com.allancleitonppma.sscagent.application.picking;

import com.allancleitonppma.sscagent.domain.model.entities.pickingEntities.InterpretedOrder;
import com.allancleitonppma.sscagent.domain.model.entities.productEntities.StockBox;
import com.allancleitonppma.sscagent.domain.model.entities.stockEntities.Pallet;

import java.util.ArrayList;
import java.util.List;

public class PickingMap {
    private InterpretedOrder order;
    private List<Pallet> pallets = new ArrayList<>();
    private List<StockBox> boxes = new ArrayList<>();



}
