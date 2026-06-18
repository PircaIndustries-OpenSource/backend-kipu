package com.kipu.backend.iotmonitoring.concretecuring.infrastructure.persistence.jpa.assemblers;

import com.kipu.backend.iotmonitoring.concretecuring.domain.model.aggregates.ConcreteCuringSensor;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.valueobjects.SensorId;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.valueobjects.Temperature;
import com.kipu.backend.iotmonitoring.concretecuring.infrastructure.persistence.jpa.embeddables.TemperaturePersistenceEmbeddable;
import com.kipu.backend.iotmonitoring.concretecuring.infrastructure.persistence.jpa.entities.ConcreteCuringSensorPersistenceEntity;

/**
 * Static assembler between concrete curing domain and persistence representations.
 */
public final class ConcreteCuringSensorPersistenceAssembler {

    private ConcreteCuringSensorPersistenceAssembler() {
    }

    /**
     * Maps a persistence entity to its corresponding domain aggregate root.
     * * @param entity The {@link ConcreteCuringSensorPersistenceEntity} from the database.
     * @return A fully populated {@link ConcreteCuringSensor} aggregate root.
     */
    public static ConcreteCuringSensor toDomainFromPersistence(ConcreteCuringSensorPersistenceEntity entity) {
        return new ConcreteCuringSensor(
                entity.getId(), // Método disponible por herencia de AuditableAbstractPersistenceEntity
                entity.getProjectId(),
                entity.getSensorId(),
                entity.getState(),
                entity.getLocation(),
                toDomainFromPersistence(entity.getTemperature()),
                entity.getHumidity(), // Se mapea directo gracias al HumidityPersistenceConverter
                entity.getTemperatureLimit()
        );
    }

    /**
     * Maps a domain aggregate root to its corresponding persistence entity representation.
     * * @param concreteCuring The {@link ConcreteCuringSensor} domain aggregate root.
     * @return A populated {@link ConcreteCuringSensorPersistenceEntity} ready for JPA save.
     */
    public static ConcreteCuringSensorPersistenceEntity toPersistenceFromDomain(ConcreteCuringSensor concreteCuringSensor) {
        var entity = new ConcreteCuringSensorPersistenceEntity();
        entity.setId(concreteCuringSensor.getId()); // Setter disponible por herencia
        entity.setProjectId(concreteCuringSensor.getProjectId());
        entity.setSensorId(new SensorId(concreteCuringSensor.getSensorId()));
        entity.setState(concreteCuringSensor.getState());
        entity.setLocation(concreteCuringSensor.getLocation());
        entity.setTemperature(toPersistenceFromDomain(concreteCuringSensor.getTemperatureValue()));
        entity.setHumidity(concreteCuringSensor.getHumidityValue()); // Se asigna directo, JPA se encarga del resto
        entity.setTemperatureLimit(concreteCuringSensor.getTemperatureLimit());
        return entity;
    }

    // --- Helper Mappers for Embeddables ---

    private static Temperature toDomainFromPersistence(TemperaturePersistenceEmbeddable value) {
        return value == null ? null : new Temperature(value.getReading(), value.getUnit());
    }

    private static TemperaturePersistenceEmbeddable toPersistenceFromDomain(Temperature value) {
        return value == null ? null : new TemperaturePersistenceEmbeddable(value.reading(), value.unit());
    }
}