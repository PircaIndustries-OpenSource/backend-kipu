package com.kipu.backend.iotmonitoring.concretecuring.domain.model.valueobjects;

/**
 * Temperature Value Object.
 * Represents a temperature measurement from IoT Concrete Curing Sensors.
 */
public record Temperature (Double reading, String unit) {

    /**
     * Gets the full formatted measurement string.
     * @return Full measure representation (e.g., "25.5 °C")
     */
    public String getFullMeasure() {
        return "%.1f %s".formatted(reading, unit);
    }

    /**
     * Compact constructor with structural validation.
     * @param unit Unit of measurement (e.g. "°C", "°F")
     * @param reading The numerical value from the sensor
     */
    public Temperature {
        if (reading == null || reading < 0.0){
            throw new IllegalArgumentException("reading must not be null or less than zero.");
        }
        if (unit == null || unit.isBlank()){
            throw new IllegalArgumentException("Unit must not be null or blank.");
        }
    }
}
