package com.allancleitonppma.sscagent.infrastructure.adapters.excel;

import java.util.List;

import com.allancleitonppma.sscagent.application.ports.StockBoxReader;
import com.allancleitonppma.sscagent.domain.model.entities.stockEntities.Address;
import com.allancleitonppma.sscagent.domain.model.entities.productEntities.StockBox;
import com.allancleitonppma.sscagent.infrastructure.dto.BoxStockDTO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class ExcelLoaderStockBox implements StockBoxReader {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final Path arquivo;

    public ExcelLoaderStockBox(Path path){
        this.arquivo = path;
    }

    public List<BoxStockDTO> load() throws IOException {

        List<BoxStockDTO> boxes = new ArrayList<>();

        try (InputStream inputStream = Files.newInputStream(arquivo);
             Workbook workbook = criarWorkbook(arquivo, inputStream)) {

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

                if (etiquetaProduto.isBlank()) {
                    continue;
                }

                BoxStockDTO dtoBox = new BoxStockDTO(
                        getString(row.getCell(0)),
                        getString(row.getCell(1)),
                        getLong(row.getCell(2)),
                        getLong(row.getCell(3)),
                        getLong(row.getCell(4)),
                        getString(row.getCell(5)),
                        getInteger(row.getCell(6)),
                        getString(row.getCell(7)),
                        getBigDecimal(row.getCell(13)),
                        getInteger(row.getCell(14)),
                        getString(row.getCell(15)),
                        getString(row.getCell(16))

                );

                boxes.add(dtoBox);
            }
        }

        return boxes;
    }

    private Workbook criarWorkbook(Path arquivo, InputStream inputStream)
            throws IOException {

        String nome = arquivo.getFileName()
                .toString()
                .toLowerCase();

        if (nome.endsWith(".xls")) {
            return new HSSFWorkbook(inputStream);
        }

        if (nome.endsWith(".xlsx")) {
            return new XSSFWorkbook(inputStream);
        }

        throw new IllegalArgumentException(
                "Formato de arquivo não suportado: " + arquivo
        );
    }

    private String getString(Cell cell) {

        if (cell == null) {
            return "";
        }

        DataFormatter formatter = new DataFormatter();

        return formatter.formatCellValue(cell).trim();
    }

    private Long getLong(Cell cell) {

        String valor = getString(cell);

        if (valor.isBlank()) {
            return null;
        }

        return Long.parseLong(valor.replace(",", ".").split("\\.")[0]);
    }

    private Integer getInteger(Cell cell) {

        String valor = getString(cell);

        if (valor.isBlank()) {
            return null;
        }

        return Integer.parseInt(valor.replace(",", ".").split("\\.")[0]);
    }

    private BigDecimal getBigDecimal(Cell cell) {

        String valor = getString(cell);

        if (valor.isBlank()) {
            return null;
        }

        return new BigDecimal(valor.replace(",", "."));
    }

    private LocalDate getData(Cell cell) {

        if (cell == null) {
            return null;
        }

        if (cell.getCellType() == CellType.NUMERIC
                && DateUtil.isCellDateFormatted(cell)) {

            return cell.getLocalDateTimeCellValue().toLocalDate();
        }

        String valor = getString(cell);

        if (valor.isBlank()) {
            return null;
        }

        return LocalDate.parse(valor, DATE_FORMAT);
    }

    private StockBox mapBoxStock(BoxStockDTO boxStockDTO) {
        StockBox box = new StockBox();

        box.boxId = String.valueOf(boxStockDTO.getProductId());
        box.address = new Address(boxStockDTO.getAddress(),boxStockDTO.getAddress(),boxStockDTO.getAddress(),boxStockDTO.getAddress());
        box.packagesPerBox = boxStockDTO.getPackages();
        box.daysToExpiry = boxStockDTO.getDaysToExpire();
        box.productCode = String.valueOf(boxStockDTO.getSankhyaId());
        box.palletId = boxStockDTO.getMotherId();
        box.expirationDate = LocalDate.parse(boxStockDTO.getValidity(), DATE_FORMAT);
        box.productName = boxStockDTO.getProductDescription();
        box.NetWeight = boxStockDTO.getDaysToExpire();

        return box;
    }

    @Override
    public List<StockBox> StockBoxLoad() throws IOException {
        return load()
                .stream()
                .map(this::mapBoxStock)
                .toList();
    }
}
