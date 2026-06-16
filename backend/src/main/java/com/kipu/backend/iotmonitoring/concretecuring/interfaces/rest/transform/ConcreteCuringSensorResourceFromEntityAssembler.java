package com.kipu.backend.iotmonitoring.concretecuring.interfaces.rest.transform;

import com.kipu.backend.iotmonitoring.concretecuring.domain.model.aggregates.ConcreteCuringSensor;
import com.kipu.backend.iotmonitoring.concretecuring.interfaces.rest.resources.ConcreteCuringSensorResource;

/**
 * Assembler to convert a ConcreteCuringSensor entity to a ConcreteCuringSensorResource.
 */
public class ConcreteCuringSensorResourceFromEntityAssembler {

    /**
     * Converts a ConcreteCuringSensor entity to a ConcreteCuringSensorResource.
     *
     * @param entity The {@link ConcreteCuringSensor} entity to convert.
     * @return The {@link ConcreteCuringSensorResource} resource.
     */
    public static ConcreteCuringSensorResource toResourceFromEntity(ConcreteCuringSensor entity) {
        // Concatenamos el valor numérico y la unidad en un único String legible (Ej: "25.4 CELSIUS")
        String formattedTemperature = String.format("%s %s",
                entity.getTemperatureReading(),
                entity.getTemperatureUnit());

        return new ConcreteCuringSensorResource(
                entity.getId(),
                entity.getProjectId(),
                entity.getSensorId(),
                entity.getState().name(), // Convertimos el Enum interno a su nombre en String (Ej: "IN_PROGRESS")
                entity.getLocation(),
                formattedTemperature,
                entity.getHumidityPercentage(), // Pasa directo como tu Integer de negocio
                entity.getTemperatureLimit()
        );
    }
}