package com.allancleitonppma.sscagent.infrastructure.Utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ExcelManipulation {
    public static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static Workbook criarWorkbook(Path path, InputStream inputStream)
            throws IOException {

        String nome = path.getFileName()
                .toString()
                .toLowerCase();

        if (nome.endsWith(".xls")) {
            return new HSSFWorkbook(inputStream);
        }

        if (nome.endsWith(".xlsx")) {
            return new XSSFWorkbook(inputStream);
        }

        throw new IllegalArgumentException(
                "Formato de path não suportado: " + path
        );
    }

    public static String getString(Cell cell) {

        if (cell == null) {
            return "";
        }

        DataFormatter formatter = new DataFormatter();

        return formatter.formatCellValue(cell).trim();
    }

    public static Long getLong(Cell cell) {

        String valor = getString(cell);

        if (valor.isBlank()) {
            return null;
        }

        return Long.parseLong(valor.replace(",", ".").split("\\.")[0]);
    }

    public static Integer getInteger(Cell cell) {

        String valor = getString(cell);

        if (valor.isBlank()) {
            return null;
        }

        return Integer.parseInt(valor.replace(",", ".").split("\\.")[0]);
    }

    public static BigDecimal getBigDecimal(Cell cell) {

        String valor = getString(cell);

        if (valor.isBlank()) {
            return null;
        }

        return new BigDecimal(valor.replace(",", "."));
    }

    public static LocalDate getData(Cell cell) {

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
}
