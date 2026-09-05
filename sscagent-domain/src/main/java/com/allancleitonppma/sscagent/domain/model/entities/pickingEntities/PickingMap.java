package com.allancleitonppma.sscagent.domain.model.entities.pickingEntities;

import com.allancleitonppma.sscagent.domain.model.entities.mapResultEntities.PickingLine;
import com.allancleitonppma.sscagent.domain.model.entities.productEntities.StockBox;
import com.allancleitonppma.sscagent.domain.model.entities.stockEntities.Pallet;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PickingMap {
    private final String id = UUID.randomUUID().toString();
    private List<InterpretedOrder> order;
    private final List<Pallet> pallets = new ArrayList<>();
    private final List<StockBox> boxes = new ArrayList<>();
    private Pallet palletToRemoveBoxes;
    private Integer quantityToRemove;

    private PickingLine pickingLine;

    public String getId() {
        return id;
    }

    public List<InterpretedOrder> getOrder() {
        return order;
    }

    public List<Pallet> getPallets() {
        return pallets;
    }

    public List<StockBox> getBoxes() {
        return boxes;
    }

    public Pallet getPalletToRemoveBoxes() {
        return palletToRemoveBoxes;
    }

    public Integer getQuantityToRemove() {
        return quantityToRemove;
    }

    public PickingLine getPickingLine() {
        return pickingLine;
    }
}
