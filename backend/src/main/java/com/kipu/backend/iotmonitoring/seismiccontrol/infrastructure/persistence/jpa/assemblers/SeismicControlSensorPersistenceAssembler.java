package com.kipu.backend.iotmonitoring.seismiccontrol.infrastructure.persistence.jpa.assemblers;

import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.aggregates.SeismicControlSensor;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.valueobjects.SeismicControlSensorState;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.valueobjects.SeismicTelemetry;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.valueobjects.SensorId;
import com.kipu.backend.iotmonitoring.seismiccontrol.infrastructure.persistence.jpa.embeddables.SeismicTelemetryPersistenceEmbeddable;
import com.kipu.backend.iotmonitoring.seismiccontrol.infrastructure.persistence.jpa.entities.SeismicControlSensorPersistenceEntity;

/**
 * Static assembler between seismic control domain and persistence representations.
 * <p>Handles data mapping isolation between the pure Domain layer and the relational
 * Infrastructure persistence model.</p>
 */
public final class SeismicControlSensorPersistenceAssembler {

    private SeismicControlSensorPersistenceAssembler() {
    }

    /**
     * Maps a JPA persistence entity into a rich Domain Aggregate Root.
     *
     * @param entity The {@link SeismicControlSensorPersistenceEntity} from the database.
     * @return A fully instantiated and validated {@link SeismicControlSensor} aggregate.
     */
    public static SeismicControlSensor toDomainFromPersistence(SeismicControlSensorPersistenceEntity entity) {
        if (entity == null) return null;

        return new SeismicControlSensor(
                entity.getId(),
                entity.getProjectId(),
                entity.getSensorId(),
                entity.getUnit(),
                toDomainFromPersistence(entity.getTelemetry()),
                entity.getLocation(),
                entity.getTimeLecture(),
                SeismicControlSensorState.fromInteger(entity.getState())
        );
    }

    /**
     * Maps a rich Domain Aggregate Root into a JPA persistence entity.
     *
     * @param seismicControl The {@link SeismicControlSensor} aggregate root.
     * @return A populated {@link SeismicControlSensorPersistenceEntity} ready for Hibernate.
     */
    public static SeismicControlSensorPersistenceEntity toPersistenceFromDomain(SeismicControlSensor seismicControl) {
        if (seismicControl == null) return null;

        var entity = new SeismicControlSensorPersistenceEntity();
        entity.setId(seismicControl.getId());
        entity.setProjectId(seismicControl.getProjectId());
        entity.setSensorId(new SensorId(seismicControl.getSensorId()));
        entity.setUnit(seismicControl.getUnit());
        entity.setLocation(seismicControl.getLocation());
        entity.setTimeLecture(seismicControl.getTimeLecture());
        entity.setState(seismicControl.getState());

        // Se extrae el Value Object del dominio y se pasa al método sobrecargado correcto
        entity.setTelemetry(toPersistenceFromDomain(seismicControl.getTelemetryValue()));

        return entity;
    }

    private static SeismicTelemetry toDomainFromPersistence(SeismicTelemetryPersistenceEmbeddable value) {
        return value == null ? null : new SeismicTelemetry(value.getLastLecture(), value.getLimit());
    }

    private static SeismicTelemetryPersistenceEmbeddable toPersistenceFromDomain(SeismicTelemetry value) {
        return value == null ? null : new SeismicTelemetryPersistenceEmbeddable(value.lastLecture(), value.limit());
    }
}