package com.allancleitonppma.sscagent.infrastructure.adapters.excel;

import com.allancleitonppma.sscagent.application.ports.PalletReader;
import com.allancleitonppma.sscagent.domain.model.entities.productEntities.StockBox;
import com.allancleitonppma.sscagent.domain.model.entities.stockEntities.Address;
import com.allancleitonppma.sscagent.domain.model.entities.stockEntities.Pallet;
import com.allancleitonppma.sscagent.infrastructure.dto.BoxStockDTO;
import com.allancleitonppma.sscagent.infrastructure.dto.IdIntegration;
import com.allancleitonppma.sscagent.infrastructure.dto.PalletDto;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.allancleitonppma.sscagent.infrastructure.Utils.ExcelManipulation.*;


public class ExcelLoaderPallet implements PalletReader {

    private final Path arquivo;

    public ExcelLoaderPallet(Path path){
        this.arquivo = path;
    }

    public PalletDto load(String idPallet) throws IOException {

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
                String etiquetaPallet = getString(row.getCell(1));
                String idProduct = getString(row.getCell(3));

                if (etiquetaPallet.isBlank()) {
                    continue;
                }

                if(idProduct.equals(idPallet)) {
                    return new PalletDto(
                            getString(row.getCell(0)),
                            getString(row.getCell(1)),
                            getString(row.getCell(2)),
                            getDouble(row.getCell(3)),
                            getInteger(row.getCell(4)),
                            getString(row.getCell(5)),
                            getString(row.getCell(6)),
                            getInteger(row.getCell(7)),
                            getInteger(row.getCell(8))

                    );

                }
            }
        }

        return null;
    }

    private List<PalletDto> loadAll(String code) throws IOException {

        List<PalletDto> pallets = new ArrayList<>();

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
                String etiquetaPallet = getString(row.getCell(0));
                String codepalle = getString(row.getCell(2));

                if (etiquetaPallet.isBlank()) {
                    continue;
                }

                if(codepalle.equals(code)) {
                    PalletDto palletDto = new PalletDto(
                            getString(row.getCell(0)),
                            getString(row.getCell(1)),
                            getString(row.getCell(2)),
                            getDouble(row.getCell(3)),
                            getInteger(row.getCell(4)),
                            getString(row.getCell(5)),
                            getString(row.getCell(6)),
                            getInteger(row.getCell(7)),
                            getInteger(row.getCell(8))

                    );
                    pallets.add(palletDto);
                }



            }
        }



        return pallets;
    }

    private Pallet mapPallet(PalletDto palletDto) {
        Pallet pallet = new Pallet();

        pallet.setPalletID(palletDto.etiquetaPalet);
        pallet.setStatus(palletDto.situacao);
        pallet.setProductCode(palletDto.apontamento);
        pallet.setProductDescription(palletDto.produto);
        pallet.setAddress(new Address(null,null, null, null));
        pallet.setSankhyaId(IdIntegration.getIntegrationIds().get(palletDto.apontamento));

        return pallet;
    }

    @Override
    public Pallet palletLoad(String id) throws IOException {
        return mapPallet(load(id));
    }

    @Override
    public List<Pallet> palletLoadAll(String code) throws IOException {
        return loadAll(code)
                .stream()
                .map(this::mapPallet)
                .toList();
    }

}
