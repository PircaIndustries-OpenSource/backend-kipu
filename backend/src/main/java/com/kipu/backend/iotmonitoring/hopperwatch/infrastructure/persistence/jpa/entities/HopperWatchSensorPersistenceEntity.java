package com.kipu.backend.iotmonitoring.hopperwatch.infrastructure.persistence.jpa.entities;

import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.valueobjects.HopperSensorState;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.valueobjects.SensorId;
import com.kipu.backend.iotmonitoring.hopperwatch.infrastructure.persistence.jpa.converters.HopperSensorStatePersistenceConverter;
import com.kipu.backend.iotmonitoring.hopperwatch.infrastructure.persistence.jpa.converters.SensorIdPersistenceConverter;
import com.kipu.backend.iotmonitoring.hopperwatch.infrastructure.persistence.jpa.embeddables.HopperMeasurementPersistenceEmbeddable;
import com.kipu.backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;

/**
 * JPA persistence entity for hopper watch monitors.
 */
@Entity
@Table(name = "hopper_watch_sensors")
public class HopperWatchSensorPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "project_id", nullable = false)
    private String projectId;

    @Convert(converter = SensorIdPersistenceConverter.class)
    @Column(name = "sensor_id", nullable = false, unique = true)
    private SensorId sensorId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Convert(converter = HopperSensorStatePersistenceConverter.class)
    @Column(name = "state", nullable = false)
    private HopperSensorState state;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lastLecture", column = @Column(name = "last_lecture")),
            @AttributeOverride(name = "safetyLimit", column = @Column(name = "safety_limit"))})
    private HopperMeasurementPersistenceEmbeddable measurement;

    public HopperWatchSensorPersistenceEntity() {
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public HopperSensorState getState() {
        return state;
    }

    public void setState(HopperSensorState state) {
        this.state = state;
    }

    public HopperMeasurementPersistenceEmbeddable getMeasurement() {
        return measurement;
    }

    public void setMeasurement(HopperMeasurementPersistenceEmbeddable measurement) {
        this.measurement = measurement;
    }
}