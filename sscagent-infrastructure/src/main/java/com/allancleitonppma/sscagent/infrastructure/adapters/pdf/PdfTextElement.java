package com.allancleitonppma.sscagent.infrastructure.adapters.pdf;

public record PdfTextElement(
        String text,
        float x,
        float y,
        float width,
        float height,
        int page
) {
}