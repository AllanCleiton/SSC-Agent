package com.allancleitonppma.sscagent.application.ports;

import com.allancleitonppma.sscagent.domain.model.entities.stockEntities.Pallet;

import java.io.IOException;
import java.util.List;

public interface PalletReader {
    Pallet palletLoad(String idPallet) throws IOException;

    List<Pallet> palletLoadAll(String code) throws IOException;
}
