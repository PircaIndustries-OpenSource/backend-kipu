package com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.valueobjects;

import java.util.regex.Pattern;

/**
 * SensorId Value Object.
 */
public record SensorId(String value) {
    // Expresión regular que exige mayúsculas, números y guiones (ej. S-VIB-02, SNS-IOT-99X)
    private static final Pattern SENSOR_PATTERN =
            Pattern.compile("^[A-Z0-9-]+$");

    /**
     * Constructor with validation.
     * @param value The raw hardware sensor identifier string.
     */
    public SensorId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Sensor ID identifier must not be null or blank");
        }
        if (!SENSOR_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Sensor ID must follow a valid hardware format (uppercase alphanumeric and hyphens only)");
        }
    }

    public String getValue() {
        return value;
    }
}