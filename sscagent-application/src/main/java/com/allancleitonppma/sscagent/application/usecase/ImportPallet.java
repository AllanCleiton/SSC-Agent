package com.allancleitonppma.sscagent.application.usecase;

import com.allancleitonppma.sscagent.application.ports.PalletReader;
import com.allancleitonppma.sscagent.domain.model.entities.stockEntities.Pallet;

import java.io.IOException;
import java.util.List;

public class ImportPallet {
    private final PalletReader palletReader;

    public ImportPallet(PalletReader palletReader) {
        this.palletReader = palletReader;
    }

    private Pallet LoadPallet(String idPallet) throws IOException {
        return  palletReader.palletLoad(idPallet);
    }

    private List<Pallet> palletLoadAll(String idPallet) throws IOException {
        return  palletReader.palletLoadAll(idPallet);
    }


}
