package com.allancleitonppma.sscagent.infrastructure;

import com.allancleitonppma.sscagent.domain.model.entities.productEntities.StockBox;
import com.allancleitonppma.sscagent.infrastructure.adapters.excel.ExcelLoaderStockBox;
import com.allancleitonppma.sscagent.infrastructure.dto.BoxStockDTO;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Main {
    static void main() throws IOException {
        ExcelLoaderStockBox loaderStockBox = new ExcelLoaderStockBox(Path.of("C:\\Users\\allan\\Documents\\MyWorkspace\\SSCAgent\\SSCAGENT\\sscagent-desktop\\src\\main\\resources\\Data\\StockBox.xls"));
        List<BoxStockDTO> stockBoxes = loaderStockBox.load();

        stockBoxes.forEach(stockBox -> System.out.println(stockBox.toString()));
    }
}
