package com.allancleitonppma.sscagent.application.ports;

import com.allancleitonppma.sscagent.domain.model.entities.SalesLoad;

import java.nio.file.Path;

public interface SalesLoadReader {

    SalesLoad read(Path file);
}
