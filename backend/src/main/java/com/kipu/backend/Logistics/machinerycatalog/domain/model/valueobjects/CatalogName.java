package com.kipu.backend.Logistics.machinerycatalog.domain.model.valueobjects;

import java.util.regex.Pattern;

public record CatalogName(String value) {
    private static final int MAX_LENGTH = 200;
    private static final String PATTERN = "^[a-zA-ZáéíóúñÑüÜ\\s\\-_0-9]+$";
    private static final Pattern COMPILED_PATTERN = Pattern.compile(PATTERN);

    public CatalogName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("machinerycatalog.error.name.notBlank");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("machinerycatalog.error.name.size");
        }
        if (!COMPILED_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("machinerycatalog.error.name.pattern");
        }
    }

    @Override
    public String toString() {
        return value();
    }
}
