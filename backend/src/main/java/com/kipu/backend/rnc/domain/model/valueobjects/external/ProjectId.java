package com.kipu.backend.rnc.domain.model.valueobjects.external;

/**
 * Value Object representing an external reference to a Project.
 */
public record ProjectId(String value) {
    public ProjectId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("ProjectId cannot be null or empty.");
        }
    }
}