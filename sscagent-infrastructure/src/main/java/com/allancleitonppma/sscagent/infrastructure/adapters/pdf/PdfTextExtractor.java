package com.allancleitonppma.sscagent.infrastructure.adapters.pdf;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PdfTextExtractor extends PDFTextStripper {

    private final List<PdfTextElement> elements = new ArrayList<>();

    public PdfTextExtractor() throws IOException {
        super();

        setSortByPosition(true);
    }

    @Override
    protected void writeString(
            String text,
            List<TextPosition> textPositions
    ) throws IOException {

        if (text == null || text.isBlank() || textPositions.isEmpty()) {
            return;
        }

        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        float maxX = Float.NEGATIVE_INFINITY;
        float maxY = Float.NEGATIVE_INFINITY;

        for (TextPosition position : textPositions) {

            float x = position.getXDirAdj();
            float y = position.getYDirAdj();

            float width = position.getWidthDirAdj();
            float height = position.getHeightDir();

            minX = Math.min(minX, x);
            minY = Math.min(minY, y);

            maxX = Math.max(maxX, x + width);
            maxY = Math.max(maxY, y + height);
        }

        elements.add(
                new PdfTextElement(
                        text.trim(),
                        minX,
                        minY,
                        maxX - minX,
                        maxY - minY,
                        getCurrentPageNo()
                )
        );
    }

    public List<PdfTextElement> extract(File file) throws IOException {

        elements.clear();

        try (PDDocument document = Loader.loadPDF(file)) {

            getText(document);
        }

        return List.copyOf(elements);
    }
}