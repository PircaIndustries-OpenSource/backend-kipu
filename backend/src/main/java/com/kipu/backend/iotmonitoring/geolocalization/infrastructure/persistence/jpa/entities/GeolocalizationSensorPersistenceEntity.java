package com.kipu.backend.iotmonitoring.geolocalization.infrastructure.persistence.jpa.entities;

import com.kipu.backend.iotmonitoring.geolocalization.infrastructure.persistence.jpa.converters.GeolocalizationSensorStatePersistenceConverter;
import com.kipu.backend.iotmonitoring.geolocalization.infrastructure.persistence.jpa.embeddables.CoordinatesPersistenceEmbeddable;
import com.kipu.backend.iotmonitoring.geolocalization.domain.model.valueobjects.GeolocalizationSensorState;
import com.kipu.backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;

/**
 * JPA persistence entity for geolocalization assets.
 */
@Entity
@Table(name = "geolocalization_assets")
public class GeolocalizationSensorPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "project_id", nullable = false)
    private String projectId;

    @Column(name = "sensor_id")
    private String sensorId; // Mantenido primitivo para consistencia con Concrete Curing

    @Column(name = "number_id")
    private Integer numberId;

    @Column(name = "name", nullable = false)
    private String name;

    @Convert(converter = GeolocalizationSensorStatePersistenceConverter.class)
    @Column(name = "state", nullable = false)
    private GeolocalizationSensorState state; // Mapeado directamente al Enum rico del dominio

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "longitude", column = @Column(name = "longitude")),
            @AttributeOverride(name = "latitude", column = @Column(name = "latitude"))})
    private CoordinatesPersistenceEmbeddable coordinates;

    public GeolocalizationSensorPersistenceEntity() {
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public Integer getNumberId() {
        return numberId;
    }

    public void setNumberId(Integer numberId) {
        this.numberId = numberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeolocalizationSensorState getState() {
        return state;
    }

    public void setState(GeolocalizationSensorState state) {
        this.state = state;
    }

    public CoordinatesPersistenceEmbeddable getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(CoordinatesPersistenceEmbeddable coordinates) {
        this.coordinates = coordinates;
    }
}