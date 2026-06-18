package com.kipu.backend.iotmonitoring.concretecuring.infrastructure.persistence.jpa.entities;

import com.kipu.backend.iotmonitoring.concretecuring.domain.model.valueobjects.CuringSensorState;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.valueobjects.Humidity;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.valueobjects.SensorId;
import com.kipu.backend.iotmonitoring.concretecuring.infrastructure.persistence.jpa.converters.ConcreteCuringSensorStatePersistenceConverter;
import com.kipu.backend.iotmonitoring.concretecuring.infrastructure.persistence.jpa.converters.HumidityPersistenceConverter;
import com.kipu.backend.iotmonitoring.concretecuring.infrastructure.persistence.jpa.embeddables.TemperaturePersistenceEmbeddable;
import com.kipu.backend.iotmonitoring.concretecuring.infrastructure.persistence.jpa.converters.SensorIdPersistenceConverter;
import com.kipu.backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;

/**
 * JPA persistence entity for concrete curing processes.
 */
@Entity
@Table(name = "concrete_curing")
public class ConcreteCuringSensorPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "project_id", nullable = false)
    private String projectId;

    @Convert(converter = SensorIdPersistenceConverter.class)
    @Column(name = "sensor_id", nullable = false, unique = true)
    private SensorId sensorId;

    @Convert(converter = ConcreteCuringSensorStatePersistenceConverter.class)
    @Column(name = "state", nullable = false)
    private CuringSensorState state;

    @Column(name = "location")
    private String location;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "reading", column = @Column(name = "temperature_reading")),
            @AttributeOverride(name = "unit", column = @Column(name = "temperature_unit"))
    })
    private TemperaturePersistenceEmbeddable temperature;

    @Convert(converter = HumidityPersistenceConverter.class)
    @Column(name = "humidity", nullable = false)
    private Humidity humidity;

    @Column(name = "temperature_limit", nullable = false)
    private Double temperatureLimit;

    public ConcreteCuringSensorPersistenceEntity() {
    }

    // --- Getters and Setters ---

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public SensorId getSensorId() {
        return sensorId;
    }

    public void setSensorId(SensorId sensorId) {
        this.sensorId = sensorId;
    }

    public CuringSensorState getState() {
        return state;
    }

    public void setState(CuringSensorState state) {
        this.state = state;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public TemperaturePersistenceEmbeddable getTemperature() {
        return temperature;
    }

    public void setTemperature(TemperaturePersistenceEmbeddable temperature) {
        this.temperature = temperature;
    }

    public Humidity getHumidity() {
        return humidity;
    }

    public void setHumidity(Humidity humidity) {
        this.humidity = humidity;
    }

    public Double getTemperatureLimit() {
        return temperatureLimit;
    }

    public void setTemperatureLimit(Double temperatureLimit) {
        this.temperatureLimit = temperatureLimit;
    }
}