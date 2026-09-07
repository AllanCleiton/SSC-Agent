package com.allancleitonppma.sscagent.infrastructure.adapters.excel;

import java.util.List;

import com.allancleitonppma.sscagent.application.ports.StockBoxReader;
import com.allancleitonppma.sscagent.domain.model.entities.stockEntities.Address;
import com.allancleitonppma.sscagent.domain.model.entities.productEntities.StockBox;
import com.allancleitonppma.sscagent.infrastructure.Utils.DefaultAddressParser;
import com.allancleitonppma.sscagent.infrastructure.config.AddressProfileLoader;
import com.allancleitonppma.sscagent.infrastructure.dto.AddressPattern;
import com.allancleitonppma.sscagent.infrastructure.dto.BoxStockDTO;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import static com.allancleitonppma.sscagent.infrastructure.Utils.ExcelManipulation.*;


public class ExcelLoaderStockBox implements StockBoxReader {

    private final Path arquivo;
    private final  Map<String, Integer> addressProfile;

    public ExcelLoaderStockBox(Path path) throws IOException {
        this.arquivo = path;
        AddressProfileLoader addressProfileLoader = new AddressProfileLoader();
        addressProfile = addressProfileLoader.load(Path.of("C:\\Users\\allan\\Documents\\MyWorkspace\\SSCAgent\\SSCAGENT\\sscagent-infrastructure\\src\\main\\resources\\addressProfile.yaml")).getAddressProfile();
    }

    public BoxStockDTO load(String id) throws IOException {

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
                String idProduct = getString(row.getCell(3));

                if (etiquetaProduto.isBlank()) {
                    continue;
                }

                if(idProduct.equals(id)) {

                    return new BoxStockDTO(
                            getString(row.getCell(0)),
                            getString(row.getCell(1)),
                            getLong(row.getCell(2)),
                            getLong(row.getCell(3)),
                            getLong(row.getCell(4)),
                            getString(row.getCell(5)),
                            getInteger(row.getCell(6)),
                            getString(row.getCell(7)),
                            getDouble(row.getCell(13)),
                            getInteger(row.getCell(14)),
                            getString(row.getCell(15)),
                            getString(row.getCell(16))

                    );

                }
            }
        }



        return null;
    }

    private List<BoxStockDTO> loadAll(String productCode) throws IOException {

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
                String idProduct = getString(row.getCell(4));

                if (etiquetaProduto.isBlank()) {
                    continue;
                }

                if(idProduct.equals(productCode)) {
                    BoxStockDTO dtoBox = new BoxStockDTO(
                            getString(row.getCell(0)),
                            getString(row.getCell(1)),
                            getLong(row.getCell(2)),
                            getLong(row.getCell(3)),
                            getLong(row.getCell(4)),
                            getString(row.getCell(5)),
                            getInteger(row.getCell(6)),
                            getString(row.getCell(7)),
                            getDouble(row.getCell(13)),
                            getInteger(row.getCell(14)),
                            getString(row.getCell(15)),
                            getString(row.getCell(16))

                    );

                    boxes.add(dtoBox);
                }
            }
        }



        return boxes;
    }

    private List<BoxStockDTO> loadAllPallet(String idPallet) throws IOException {

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
                String id = getString(row.getCell(1));

                if (etiquetaProduto.isBlank()) {
                    continue;
                }

                if(id.equals(idPallet)) {
                    BoxStockDTO dtoBox = new BoxStockDTO(
                            getString(row.getCell(0)),
                            getString(row.getCell(1)),
                            getLong(row.getCell(2)),
                            getLong(row.getCell(3)),
                            getLong(row.getCell(4)),
                            getString(row.getCell(5)),
                            getInteger(row.getCell(6)),
                            getString(row.getCell(7)),
                            getDouble(row.getCell(13)),
                            getInteger(row.getCell(14)),
                            getString(row.getCell(15)),
                            getString(row.getCell(16))

                    );
                    boxes.add(dtoBox);
                }


            }
        }


        return boxes;
    }


    private StockBox mapBoxStock(BoxStockDTO boxStockDTO){
        StockBox box = new StockBox();

        AddressPattern pattern = new AddressPattern("CAM", "R", "A");
        DefaultAddressParser addressParser = new DefaultAddressParser(pattern ,addressProfile );

        box.productId = String.valueOf(boxStockDTO.getProductId());
        box.address = addressParser.parse(boxStockDTO.getAddress());
        box.packages = boxStockDTO.getPackages();
        box.daysToExpiry = boxStockDTO.getDaysToExpire();
        box.productCode = String.valueOf(boxStockDTO.getSankhyaId());
        box.palletId = boxStockDTO.getMotherId();
        box.expirationDate = LocalDate.parse(boxStockDTO.getValidity(), DATE_FORMAT);
        box.productName = boxStockDTO.getProductDescription();
        box.NetWeight = boxStockDTO.getNetWeight();
        box.SankhyaId = boxStockDTO.getSankhyaId();
        box.productionDate = LocalDate.parse(boxStockDTO.getManufacturingDate(), DATE_FORMAT);
        box.sourceStatus = boxStockDTO.getStatus();
        return box;
    }

    @Override
    public StockBox StockBoxLoad(String id) throws IOException {
        return mapBoxStock(load(id));

    }

    @Override
    public List<StockBox> StockBoxLoadAll(String idProduct) throws IOException {
        return loadAll(idProduct)
                .stream()
                .map(this::mapBoxStock)
                .toList();
    }

    @Override
    public List<StockBox> loadAllForPallet(String idPallet) throws IOException {
        return loadAllPallet(idPallet)
                .stream()
                .map(this::mapBoxStock)
                .toList();
    }

}
