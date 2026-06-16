package com.kipu.backend.iotmonitoring.concretecuring.infrastructure.persistence.jpa.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class TemperaturePersistenceEmbeddable {

    @Column(name = "temperature")
    private Double reading;

    @Column(name = "temperature_unit")
    private String unit;

    public TemperaturePersistenceEmbeddable() {
    }

    public TemperaturePersistenceEmbeddable(Double reading, String unit) {
        this.reading = reading;
        this.unit = unit;
    }

    public Double getReading() {
        return reading;
    }

    public void setReading(Double reading) {
        this.reading = reading;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
