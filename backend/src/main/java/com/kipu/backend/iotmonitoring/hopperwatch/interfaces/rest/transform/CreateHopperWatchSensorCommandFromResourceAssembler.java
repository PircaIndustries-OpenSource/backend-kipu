package com.kipu.backend.iotmonitoring.hopperwatch.interfaces.rest.transform;

import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.commands.CreateHopperWatchSensorCommand;
import com.kipu.backend.iotmonitoring.hopperwatch.interfaces.rest.resources.CreateHopperWatchSensorResource;

/**
 * Assembler to convert a CreateHopperWatchSensorResource to a CreateHopperWatchSensorCommand.
 */
public class CreateHopperWatchSensorCommandFromResourceAssembler {

    /**
     * Converts a CreateHopperWatchSensorResource to a CreateHopperWatchSensorCommand.
     *
     * @param resource The {@link CreateHopperWatchSensorResource} resource to convert.
     * @return The {@link CreateHopperWatchSensorCommand} command.
     */
    public static CreateHopperWatchSensorCommand toCommandFromResource(CreateHopperWatchSensorResource resource) {
        return new CreateHopperWatchSensorCommand(
                resource.projectId(),
                resource.sensorId(),
                resource.name(),
                resource.unit(),
                resource.state(),
                resource.lastLecture(),
                resource.safetyLimit()
        );
    }
}