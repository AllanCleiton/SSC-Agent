package com.allancleitonppma.sscagent.infrastructure;

import com.allancleitonppma.sscagent.infrastructure.adapters.pdf.PdfSalesLoadReader;
import java.io.IOException;
import java.nio.file.Path;


public class Main {
    static void main() throws IOException {

        PdfSalesLoadReader reader = new PdfSalesLoadReader();
        reader.read(Path.of("C:\\Users\\Allan\\Downloads\\11308.pdf"));
    }
}