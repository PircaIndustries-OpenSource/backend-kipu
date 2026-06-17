package com.kipu.backend.iotmonitoring.hopperwatch.infrastructure.persistence.jpa.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Persistence representation for a hopper measurement telemetry.
 */
@Embeddable
public class HopperMeasurementPersistenceEmbeddable {

    @Column(name = "last_lecture")
    private Integer lastLecture;

    @Column(name = "safety_limit")
    private Integer safetyLimit;

    public HopperMeasurementPersistenceEmbeddable() {
    }

    public HopperMeasurementPersistenceEmbeddable(Integer lastLecture, Integer safetyLimit) {
        this.lastLecture = lastLecture;
        this.safetyLimit = safetyLimit;
    }

    public Integer getLastLecture() {
        return lastLecture;
    }

    public void setLastLecture(Integer lastLecture) {
        this.lastLecture = lastLecture;
    }

    public Integer getSafetyLimit() {
        return safetyLimit;
    }

    public void setSafetyLimit(Integer safetyLimit) {
        this.safetyLimit = safetyLimit;
    }
}