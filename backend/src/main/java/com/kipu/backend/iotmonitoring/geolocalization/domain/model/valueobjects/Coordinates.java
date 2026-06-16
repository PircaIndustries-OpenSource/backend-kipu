package com.kipu.backend.iotmonitoring.geolocalization.domain.model.valueobjects;

/**
 * Coordinates Value Object.
 */
public record Coordinates(Double longitude, Double latitude) {

    /**
     * Formatted coordinates getter
     * @return Formatted coordinates as a string "latitude, longitude"
     */
    public String getFormattedCoordinates() {
        return "%s, %s".formatted(latitude, longitude);
    }

    /**
     * Constructor with domain validation
     * @param longitude Longitude coordinate
     * @param latitude Latitude coordinate
     */
    public Coordinates {
        if (longitude == null) {
            throw new IllegalArgumentException("Longitude must not be null");
        }
        if (latitude == null) {
            throw new IllegalArgumentException("Latitude must not be null");
        }

        // Validación de reglas de negocio geoespaciales
        if (latitude < -90.0 || latitude > 90.0) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90 degrees");
        }
        if (longitude < -180.0 || longitude > 180.0) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180 degrees");
        }
    }
}