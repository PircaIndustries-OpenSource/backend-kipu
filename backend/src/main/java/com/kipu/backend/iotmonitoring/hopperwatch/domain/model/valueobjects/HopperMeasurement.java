package com.kipu.backend.iotmonitoring.hopperwatch.domain.model.valueobjects;

/**
 * HopperMeasurement Value Object.
 * Encapsulates the core telemetry metrics for a hopper.
 */
public record HopperMeasurement(Integer lastLecture, Integer safetyLimit) {

    /**
     * Checks if the current hopper level has dropped below the safety limit.
     * @return true if an alert is required, false otherwise
     */
    public boolean isBelowSafetyLimit() {
        return lastLecture < safetyLimit;
    }

    /**
     * Constructor with validation.
     * Ensures telemetry values are valid and coherent before entering the domain.
     *
     * @param lastLecture The last recorded lecture.
     * @param safetyLimit The safety threshold limit.
     */
    public HopperMeasurement {
        if (lastLecture == null) {
            throw new IllegalArgumentException("Last lecture must not be null");
        }
        if (safetyLimit == null) {
            throw new IllegalArgumentException("Safety limit must not be null");
        }
        if (lastLecture < 0) {
            throw new IllegalArgumentException("Last lecture cannot be negative");
        }
        if (safetyLimit < 0) {
            throw new IllegalArgumentException("Safety limit cannot be negative");
        }
    }
}