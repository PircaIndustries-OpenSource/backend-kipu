package com.kipu.backend.Logistics.domain.model.valueobjects.external;

public record ProjectId(String value) {
    private static final String INVALID_VALUE_MESSAGE_KEY = "project.error.projectId.invalidValue";

    public ProjectId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(INVALID_VALUE_MESSAGE_KEY);
        }
    }
}