package com.allancleitonppma.sscagent.application.ports;

import com.allancleitonppma.sscagent.domain.model.entities.orderEntities.SalesLoad;

import java.io.IOException;
import java.nio.file.Path;

public interface SalesLoadReader {

    SalesLoad read(Path file) throws IOException;
}
