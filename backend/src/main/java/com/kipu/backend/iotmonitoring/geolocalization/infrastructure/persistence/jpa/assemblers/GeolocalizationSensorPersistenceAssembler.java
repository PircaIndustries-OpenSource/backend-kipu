package com.kipu.backend.iotmonitoring.geolocalization.infrastructure.persistence.jpa.assemblers;

import com.kipu.backend.iotmonitoring.geolocalization.domain.model.aggregates.GeolocalizationSensor;
import com.kipu.backend.iotmonitoring.geolocalization.domain.model.valueobjects.Coordinates;
import com.kipu.backend.iotmonitoring.geolocalization.infrastructure.persistence.jpa.embeddables.CoordinatesPersistenceEmbeddable;
import com.kipu.backend.iotmonitoring.geolocalization.infrastructure.persistence.jpa.entities.GeolocalizationSensorPersistenceEntity;

/**
 * Static assembler between geolocalization sensor domain and persistence representations.
 */
public final class GeolocalizationSensorPersistenceAssembler {

    private GeolocalizationSensorPersistenceAssembler() {
    }

    /**
     * Maps a persistence entity to its corresponding domain aggregate root.
     * @param entity The {@link GeolocalizationSensorPersistenceEntity} from the database.
     * @return A fully populated {@link GeolocalizationSensor} domain instance.
     */
    public static GeolocalizationSensor toDomainFromPersistence(GeolocalizationSensorPersistenceEntity entity) {
        if (entity == null) {
            return null;
        }
        return new GeolocalizationSensor(
                entity.getId(),
                entity.getProjectId(),
                entity.getSensorId(),
                entity.getNumberId(),
                entity.getName(),
                entity.getState(), // El convertidor de JPA ya nos entrega el tipo seguro GeolocalizationState
                toDomainFromPersistence(entity.getCoordinates())
        );
    }

    /**
     * Maps a domain aggregate root to its corresponding persistence entity.
     * @param sensor The {@link GeolocalizationSensor} domain instance.
     * @return A fully populated {@link GeolocalizationSensorPersistenceEntity} ready for JPA tracking.
     */
    public static GeolocalizationSensorPersistenceEntity toPersistenceFromDomain(GeolocalizationSensor sensor) {
        if (sensor == null) {
            return null;
        }
        var entity = new GeolocalizationSensorPersistenceEntity();
        entity.setId(sensor.getId());
        entity.setProjectId(sensor.getProjectId());
        entity.setSensorId(sensor.getSensorId());
        entity.setNumberId(sensor.getNumberId());
        entity.setName(sensor.getName());
        entity.setState(sensor.getState()); // Se asigna el Enum; JPA se encargará de guardarlo como entero
        entity.setCoordinates(toPersistenceFromDomain(sensor.getCoordinatesValue()));
        return entity;
    }

    private static Coordinates toDomainFromPersistence(CoordinatesPersistenceEmbeddable value) {
        return value == null ? null : new Coordinates(value.getLongitude(), value.getLatitude());
    }

    private static CoordinatesPersistenceEmbeddable toPersistenceFromDomain(Coordinates value) {
        return value == null ? null : new CoordinatesPersistenceEmbeddable(value.longitude(), value.latitude());
    }
}