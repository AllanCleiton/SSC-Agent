package com.allancleitonppma.sscagent.infrastructure.adapters.excel;
import com.allancleitonppma.sscagent.application.ports.StockBoxWrite;
import com.allancleitonppma.sscagent.domain.model.entities.productEntities.StockBox;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.allancleitonppma.sscagent.infrastructure.Utils.ExcelManipulation.*;
import static com.allancleitonppma.sscagent.infrastructure.Utils.ExcelManipulation.getString;

public class ExcelWriterStockBox implements StockBoxWrite {
    private final Path path;

    public ExcelWriterStockBox(Path path){
        this.path = path;
    }


    @Override
    public Boolean StockBoxUpDate(String productId, StockBox box) {
        return null;
    }

    private boolean upDateStockBox(String productId, StockBox box) throws IOException {
        try (InputStream inputStream = Files.newInputStream(path);
             Workbook workbook = criarWorkbook(path, inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            // Ignora a primeira linha, que é o cabeçalho.
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);

                if (row == null) {
                    continue;
                }

                /*
                 * Segunda coluna = Etiq Produto.
                 *
                 * Se estiver vazia, a linha inteira é ignorada.
                 */
                String etiquetaProduto = getString(row.getCell(2));
                String idProduct = getString(row.getCell(3));

                if (etiquetaProduto.isBlank()) {
                    continue;
                }

                if(idProduct.equals(productId)) {
                            row.getCell(18).setCellValue( ((box.isAvailable) ? "true":"false")
                            );

                }
            }
        }



        return true;
    }
}
