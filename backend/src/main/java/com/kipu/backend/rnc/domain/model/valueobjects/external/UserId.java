package com.kipu.backend.rnc.domain.model.valueobjects.external;

/**
 * Value Object representing an external reference to a User.
 */
public record UserId(String value) {
    public UserId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("UserId cannot be null or empty.");
        }
    }
}