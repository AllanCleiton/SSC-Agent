package com.allancleitonppma.sscagent.application.port;

import com.allancleitonppma.sscagent.domain.model.entities.SalesLoad;

import java.nio.file.Path;

public interface SalesLoadReader {

    SalesLoad read(Path file);
}
