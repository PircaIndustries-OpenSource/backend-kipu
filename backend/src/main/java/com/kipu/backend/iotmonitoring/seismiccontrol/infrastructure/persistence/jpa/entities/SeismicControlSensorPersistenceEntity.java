package com.kipu.backend.iotmonitoring.seismiccontrol.infrastructure.persistence.jpa.entities;

import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.valueobjects.SeismicControlSensorState;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.valueobjects.SensorId;
import com.kipu.backend.iotmonitoring.seismiccontrol.infrastructure.persistence.jpa.converters.SeismicControlStatePersistenceConverter;
import com.kipu.backend.iotmonitoring.seismiccontrol.infrastructure.persistence.jpa.converters.SensorIdPersistenceConverter;
import com.kipu.backend.iotmonitoring.seismiccontrol.infrastructure.persistence.jpa.embeddables.SeismicTelemetryPersistenceEmbeddable;
import com.kipu.backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;

/**
 * JPA persistence entity for seismic controls.
 */
@Entity
@Table(name = "seismic_controls")
public class SeismicControlSensorPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "project_id", nullable = false)
    private String projectId;

    @Convert(converter = SensorIdPersistenceConverter.class)
    @Column(name = "sensor_id", nullable = false, unique = true)
    private SensorId sensorId;

    @Column(name = "measurement_unit", nullable = false)
    private String unit;

    @Column(name = "location_placement", nullable = false)
    private String location;

    @Column(name = "lecture_timestamp", nullable = false)
    private String timeLecture;

    @Convert(converter = SeismicControlStatePersistenceConverter.class)
    @Column(name = "operational_state", nullable = false)
    private SeismicControlSensorState state;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lastLecture", column = @Column(name = "last_lecture")),
            @AttributeOverride(name = "limit", column = @Column(name = "safety_limit"))
    })
    private SeismicTelemetryPersistenceEmbeddable telemetry;

    /**
     * Default constructor required exclusively by JPA/Hibernate specification.
     */
    public SeismicControlSensorPersistenceEntity() {
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTimeLecture() {
        return timeLecture;
    }

    public void setTimeLecture(String timeLecture) {
        this.timeLecture = timeLecture;
    }

    public SeismicControlSensorState getState() {
        return state;
    }

    public void setState(SeismicControlSensorState state) {
        this.state = state;
    }

    public SeismicTelemetryPersistenceEmbeddable getTelemetry() {
        return telemetry;
    }

    public void setTelemetry(SeismicTelemetryPersistenceEmbeddable telemetry) {
        this.telemetry = telemetry;
    }
}