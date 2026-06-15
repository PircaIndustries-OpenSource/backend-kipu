package com.kipu.backend.iotmonitoring.concretecuring.domain.model.valueobjects;

/**
 * Humidity Value Object
 * Represents a humidity measurement from IoT Concrete Curing Sensors
 */
public record Humidity(Integer percentage) {

    /**
     * Gets the formatted measurement string.
     * @return Full measure representation (e.g., 25%)
     */
    public String getFormatted(){
        return "%d%%".formatted(percentage);
    }

    /**
     * Compact constructor with structural validation.
     * @param percentage The numerical value from the sensor.
     */
    public Humidity {
        if (percentage == null) {
            throw new IllegalArgumentException("Humidity percentage must not be null.");
        }
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Humidity must be between 0 and 100 percent.");
        }
    }
}
