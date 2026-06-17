package com.kipu.backend.iotmonitoring.hopperwatch.infrastructure.persistence.jpa.assemblers;

import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.aggregates.HopperWatchSensor;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.valueobjects.HopperMeasurement;
import com.kipu.backend.iotmonitoring.hopperwatch.infrastructure.persistence.jpa.embeddables.HopperMeasurementPersistenceEmbeddable;
import com.kipu.backend.iotmonitoring.hopperwatch.infrastructure.persistence.jpa.entities.HopperWatchSensorPersistenceEntity;

/**
 * Static assembler between hopper watch domain and persistence representations.
 */
public final class HopperWatchSensorPersistenceAssembler {

    private HopperWatchSensorPersistenceAssembler() {
    }

    /**
     * Maps a JPA entity to a rich domain aggregate.
     * @param entity The persistence entity from the DB
     * @return A validated {@link HopperWatchSensor} aggregate root
     */
    public static HopperWatchSensor toDomainFromPersistence(HopperWatchSensorPersistenceEntity entity) {
        return new HopperWatchSensor(
                entity.getId(),
                entity.getProjectId(),
                entity.getSensorId(),
                entity.getName(),
                entity.getUnit(),
                entity.getState(),
                toDomainFromPersistence(entity.getMeasurement())
        );
    }

    /**
     * Maps a domain aggregate to a data-transfer JPA entity for database operations.
     * @param hopperWatch The rich domain aggregate root
     * @return A populated {@link HopperWatchSensorPersistenceEntity}
     */
    public static HopperWatchSensorPersistenceEntity toPersistenceFromDomain(HopperWatchSensor hopperWatch) {
        var entity = new HopperWatchSensorPersistenceEntity();
        entity.setId(hopperWatch.getId());
        entity.setProjectId(hopperWatch.getProjectId());
        entity.setSensorId(hopperWatch.getSensorId());
        entity.setName(hopperWatch.getName());
        entity.setUnit(hopperWatch.getUnit());
        entity.setState(hopperWatch.getState());
        entity.setMeasurement(toPersistenceFromDomain(hopperWatch.getMeasurementValue()));
        return entity;
    }

    private static HopperMeasurement toDomainFromPersistence(HopperMeasurementPersistenceEmbeddable value) {
        return value == null ? null : new HopperMeasurement(value.getLastLecture(), value.getSafetyLimit());
    }

    private static HopperMeasurementPersistenceEmbeddable toPersistenceFromDomain(HopperMeasurement value) {
        return value == null ? null : new HopperMeasurementPersistenceEmbeddable(value.lastLecture(), value.safetyLimit());
    }
}