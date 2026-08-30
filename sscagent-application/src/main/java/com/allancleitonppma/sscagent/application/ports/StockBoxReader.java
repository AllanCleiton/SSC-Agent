package com.allancleitonppma.sscagent.application.ports;

import com.allancleitonppma.sscagent.domain.model.entities.productEntities.StockBox;


import java.io.IOException;
import java.util.List;

public interface StockBoxReader {
    List<StockBox> StockBoxLoad(String idProduct) throws IOException;

    List<StockBox> StockBoxLoadAll(String idProduct) throws IOException;
}
