package com.allancleitonppma.sscagent.application.ports;

import com.allancleitonppma.sscagent.domain.model.entities.stockEntities.Pallet;

import java.io.IOException;
import java.util.List;

public interface PalletReader {
    Pallet PalletLoad(String idPallet) throws IOException;

    List<Pallet> PalletLoadAll(String code) throws IOException;
}
