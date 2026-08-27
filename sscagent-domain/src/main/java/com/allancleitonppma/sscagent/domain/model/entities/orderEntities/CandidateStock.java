package com.allancleitonppma.sscagent.domain.model.entities.orderEntities;

import com.allancleitonppma.sscagent.domain.model.entities.stockEntities.Pallet;
import com.allancleitonppma.sscagent.domain.model.entities.productEntities.StockBox;

import java.util.ArrayList;
import java.util.List;

public class CandidateStock {
    List<Pallet> palletList = new ArrayList<>();
    List<StockBox> stockBoxes = new ArrayList<>();

    public CandidateStock(List<Pallet> palletList, List<StockBox> stockBoxes) {
        this.palletList = palletList;
        this.stockBoxes = stockBoxes;
    }

    public CandidateStock(){}
}
