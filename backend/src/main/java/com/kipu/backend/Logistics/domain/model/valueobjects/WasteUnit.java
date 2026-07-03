package com.kipu.backend.Logistics.domain.model.valueobjects;

/**
 * Value object representing the unit of measure for a material waste record.
 * Stored as a plain string to accommodate flexible unit names (e.g., "und", "bolsa", "varilla", "m3").
 */
public record WasteUnit(String value) {

    private static final int MAX_LENGTH = 50;
    private static final String BLANK_MESSAGE_KEY = "material.waste.error.unit.notBlank";
    private static final String TOO_LONG_MESSAGE_KEY = "material.waste.error.unit.tooLong";

    public WasteUnit {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(BLANK_MESSAGE_KEY);
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(TOO_LONG_MESSAGE_KEY);
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
