package com.kipu.backend.iotmonitoring.seismiccontrol.infrastructure.persistence.jpa.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Persistence representation for seismic telemetry metrics.
 * <p>Maps the cohesive domain concepts of velocity readings and safety thresholds
 * into atomic database columns.</p>
 */
@Embeddable
public class SeismicTelemetryPersistenceEmbeddable {

    @Column(name = "last_lecture")
    private Double lastLecture;

    // Se utiliza 'safety_limit' para prevenir colisiones con la palabra reservada 'LIMIT' en SQL
    @Column(name = "safety_limit")
    private Double limit;

    /**
     * Default constructor required exclusively by JPA/Hibernate specification.
     */
    public SeismicTelemetryPersistenceEmbeddable() {
    }

    /**
     * Parameterized constructor for infrastructure mapping.
     * @param lastLecture The actual velocity numeric reading
     * @param limit The maximum safety limit threshold
     */
    public SeismicTelemetryPersistenceEmbeddable(Double lastLecture, Double limit) {
        this.lastLecture = lastLecture;
        this.limit = limit;
    }

    public Double getLastLecture() {
        return lastLecture;
    }

    public void setLastLecture(Double lastLecture) {
        this.lastLecture = lastLecture;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }
}