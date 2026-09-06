package com.allancleitonppma.sscagent.application.usecase;

import com.allancleitonppma.sscagent.application.ports.PalletReader;
import com.allancleitonppma.sscagent.application.ports.StockBoxReader;
import com.allancleitonppma.sscagent.domain.model.entities.productEntities.StockBox;
import com.allancleitonppma.sscagent.domain.model.entities.stockEntities.Pallet;

import java.io.IOException;
import java.util.List;

public class ImportStockBox {
    private final StockBoxReader stockBoxReader;

    public ImportStockBox(StockBoxReader stockBoxReader) {
        this.stockBoxReader = stockBoxReader;
    }

    public StockBox LoadStockBox(String idStockBox) throws IOException {
        return  stockBoxReader.StockBoxLoad(idStockBox);
    }

    public List<StockBox> StockBoxLoadAll(String code) throws IOException {
        return  stockBoxReader.StockBoxLoadAll(code);
    }

    public List<StockBox> loadAllForPallet(String idPallet) throws IOException {
        return  stockBoxReader.StockBoxLoadAll(idPallet);
    }


}
