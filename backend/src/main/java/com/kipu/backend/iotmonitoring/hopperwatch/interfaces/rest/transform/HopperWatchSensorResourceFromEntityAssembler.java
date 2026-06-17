package com.kipu.backend.iotmonitoring.hopperwatch.interfaces.rest.transform;

import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.aggregates.HopperWatchSensor;
import com.kipu.backend.iotmonitoring.hopperwatch.interfaces.rest.resources.HopperWatchSensorResource;

/**
 * Assembler to convert a HopperWatchSensor entity to a HopperWatchSensorResource.
 */
public class HopperWatchSensorResourceFromEntityAssembler {

    /**
     * Converts a HopperWatchSensor entity to a HopperWatchSensorResource.
     *
     * @param entity The {@link HopperWatchSensor} entity to convert.
     * @return The {@link HopperWatchSensorResource} resource.
     */
    public static HopperWatchSensorResource toResourceFromEntity(HopperWatchSensor entity) {
        return new HopperWatchSensorResource(
                entity.getId(),
                entity.getProjectId(),
                entity.getSensorId(),
                entity.getName(),
                entity.getUnit(),
                entity.getState() != null ? entity.getState().getValue() : null,
                entity.getMeasurementValue() != null ? entity.getMeasurementValue().lastLecture() : 0,
                entity.getMeasurementValue() != null ? entity.getMeasurementValue().safetyLimit() : 0
        );
    }
}