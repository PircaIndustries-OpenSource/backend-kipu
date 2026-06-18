package com.kipu.backend.iotmonitoring.hopperwatch.interfaces.rest.transform;

import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.commands.UpdateHopperWatchSensorCommand;
import com.kipu.backend.iotmonitoring.hopperwatch.interfaces.rest.resources.UpdateHopperWatchSensorResource;

public class UpdateHopperWatchSensorCommandFromResourceAssembler {
    public static UpdateHopperWatchSensorCommand toCommandFromResource(Long id, UpdateHopperWatchSensorResource resource) {
        return new UpdateHopperWatchSensorCommand(
                id,
                resource.sensorId(),
                resource.name(),
                resource.unit(),
                resource.state(),
                resource.lastLecture(),
                resource.limit()
        );
    }
}
