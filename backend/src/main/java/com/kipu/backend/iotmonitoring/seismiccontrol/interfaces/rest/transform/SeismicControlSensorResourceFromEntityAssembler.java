package com.kipu.backend.iotmonitoring.seismiccontrol.interfaces.rest.transform;

import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.aggregates.SeismicControlSensor;
import com.kipu.backend.iotmonitoring.seismiccontrol.interfaces.rest.resources.SeismicControlSensorResource;

/**
 * Assembler to convert a SeismicControlSensor entity to a SeismicControlSensorResource.
 * <p>Utility mapper that transforms domain aggregate state into outbound REST response payloads.</p>
 */
public class SeismicControlSensorResourceFromEntityAssembler {

    /**
     * Converts a SeismicControlSensor entity to a SeismicControlSensorResource.
     *
     * @param entity The {@link SeismicControlSensor} aggregate root to convert.
     * @return The {@link SeismicControlSensorResource} response DTO.
     */
    public static SeismicControlSensorResource toResourceFromEntity(SeismicControlSensor entity) {
        return new SeismicControlSensorResource(
                entity.getId(),
                entity.getProjectId(),
                entity.getSensorId(),
                entity.getUnit(),
                entity.getLastLecture(),
                entity.getLimit(),
                entity.getLocation(),
                entity.getTimeLecture(),
                entity.getState()
        );
    }
}