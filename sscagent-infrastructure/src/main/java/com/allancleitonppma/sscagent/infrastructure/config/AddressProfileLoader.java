package com.allancleitonppma.sscagent.infrastructure.config;

import com.allancleitonppma.sscagent.infrastructure.dto.AddressProfile;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.nio.file.Path;

public class AddressProfileLoader {
    private final ObjectMapper objectMapper;

    public AddressProfileLoader() {

        YAMLFactory yamlFactory = new YAMLFactory();

        this.objectMapper = new ObjectMapper(yamlFactory);
    }

    public AddressProfile load(Path path) throws IOException {

        return objectMapper.readValue(
                path.toFile(),
                AddressProfile.class
        );
    }
}