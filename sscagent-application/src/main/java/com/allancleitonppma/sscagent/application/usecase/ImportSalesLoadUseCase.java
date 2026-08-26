package com.allancleitonppma.sscagent.application.usecase;

import com.allancleitonppma.sscagent.application.ports.SalesLoadReader;
import com.allancleitonppma.sscagent.domain.model.entities.SalesLoad;


import java.nio.file.Path;

public class ImportSalesLoadUseCase {

    private final SalesLoadReader salesLoadReader;

    public ImportSalesLoadUseCase(SalesLoadReader salesLoadReader) {
        this.salesLoadReader = salesLoadReader;
    }

    public SalesLoad execute(Path file) {
        return salesLoadReader.read(file);
    }
}