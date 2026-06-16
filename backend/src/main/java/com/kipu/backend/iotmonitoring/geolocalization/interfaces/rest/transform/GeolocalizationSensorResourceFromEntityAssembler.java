package com.kipu.backend.iotmonitoring.geolocalization.interfaces.rest.transform;

import com.kipu.backend.iotmonitoring.geolocalization.domain.model.aggregates.GeolocalizationSensor;
import com.kipu.backend.iotmonitoring.geolocalization.interfaces.rest.resources.GeolocalizationSensorResource;

/**
 * Assembler to convert a GeolocalizationSensor entity to a GeolocalizationSensorResource.
 */
public class GeolocalizationSensorResourceFromEntityAssembler {

    /**
     * Converts a GeolocalizationSensor entity to a GeolocalizationSensorResource.
     * @param entity The {@link GeolocalizationSensor} entity to convert.
     * @return The {@link GeolocalizationSensorResource} resource.
     */
    public static GeolocalizationSensorResource toResourceFromEntity(GeolocalizationSensor entity) {
        return new GeolocalizationSensorResource(
                entity.getId(),
                entity.getProjectId(),
                entity.getSensorId(),
                entity.getNumberId(),
                entity.getName(),
                entity.getState().getValue(), // Extrae la forma numérica del Enum (1, 2, 3) garantizando la consistencia interna
                entity.getCoordinatesValue().longitude(), // Aplana el Value Object rico para el consumo directo de mapas en la UI
                entity.getCoordinatesValue().latitude()
        );
    }
}