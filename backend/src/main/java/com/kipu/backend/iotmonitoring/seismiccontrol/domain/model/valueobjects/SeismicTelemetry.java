package com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.valueobjects;

/**
 * SeismicTelemetry Value Object.
 */
public record SeismicTelemetry(Double lastLecture, Double limit) {

    /**
     * Checks if the current lecture has breached the safety limit.
     * @return true if limit is exceeded, false otherwise
     */
    public boolean isExceedingLimit() {
        return lastLecture > limit;
    }

    /**
     * Constructor with validation
     * @param lastLecture Last recorded velocity metric
     * @param limit Peak safety threshold allowed
     */
    public SeismicTelemetry {
        if (lastLecture == null || lastLecture < 0) {
            throw new IllegalArgumentException("Last lecture must not be null and must be greater than or equal to zero");
        }
        if (limit == null || limit <= 0) {
            throw new IllegalArgumentException("Safety limit must not be null and must be greater than zero");
        }
    }
}