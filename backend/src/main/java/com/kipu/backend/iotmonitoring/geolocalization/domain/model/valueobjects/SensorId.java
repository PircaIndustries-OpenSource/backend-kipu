package com.kipu.backend.iotmonitoring.geolocalization.domain.model.valueobjects;

public record SensorId(String value) {
    public SensorId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Sensor ID cannot be null or empty");
        }
    }
}
