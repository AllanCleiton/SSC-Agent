package com.allancleitonppma.sscagent.infrastructure.adapters.excel;

import java.util.List;

import com.allancleitonppma.sscagent.application.ports.StockBoxReader;
import com.allancleitonppma.sscagent.domain.model.entities.Address;
import com.allancleitonppma.sscagent.domain.model.entities.StockBox;
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

    ExcelLoaderStockBox(Path path){
        this.arquivo = path;
    }

    private List<BoxStockDTO> load() throws IOException {

        List<BoxStockDTO> estoque = new ArrayList<>();

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

                BoxStockDTO dto = new BoxStockDTO(
                        getString(row.getCell(0)),
                        getString(row.getCell(1)),
                        getLong(row.getCell(2)),
                        getLong(row.getCell(3)),
                        getLong(row.getCell(4)),
                        getString(row.getCell(5)),
                        getInteger(row.getCell(6)),
                        getString(row.getCell(7)),
                        getBigDecimal(row.getCell(8)),
                        getInteger(row.getCell(9)),
                        getString(row.getCell(10)),
                        getString(row.getCell(11))
                );

                estoque.add(dto);
            }
        }

        return estoque;
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

        box.boxId = String.valueOf(boxStockDTO.getEtiquetaProduto());
        box.address = new Address(boxStockDTO.getEndereco(),boxStockDTO.getEndereco(),boxStockDTO.getEndereco(),boxStockDTO.getEndereco());
        box.packagesPerBox = boxStockDTO.getPacotes();
        box.daysToExpiry = boxStockDTO.getDiasAVencer();
        box.productCode = String.valueOf(boxStockDTO.getCodigoSankhya());
        box.palletId = boxStockDTO.getEtiquetaMae();
        box.expirationDate = LocalDate.parse(boxStockDTO.getDataValidade(), DATE_FORMAT);
        box.productName = boxStockDTO.getProduto();
        box.NetWeight = boxStockDTO.getDiasAVencer();

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
