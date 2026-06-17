package com.kipu.backend.iotmonitoring.seismiccontrol.interfaces.rest.transform;

import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.commands.CreateSeismicControlSensorCommand;
import com.kipu.backend.iotmonitoring.seismiccontrol.interfaces.rest.resources.CreateSeismicControlSensorResource;

/**
 * Assembler to convert a CreateSeismicControlSensorResource to a CreateSeismicControlSensorCommand.
 * <p>Utility mapper that isolates the REST layer infrastructure from the internal application commands.</p>
 */
public class CreateSeismicControlSensorCommandFromResourceAssembler {

    /**
     * Converts a CreateSeismicControlSensorResource to a CreateSeismicControlSensorCommand.
     *
     * @param resource The {@link CreateSeismicControlSensorResource} incoming request DTO.
     * @return The fully initialized {@link CreateSeismicControlSensorCommand} execution descriptor.
     */
    public static CreateSeismicControlSensorCommand toCommandFromResource(CreateSeismicControlSensorResource resource) {
        return new CreateSeismicControlSensorCommand(
                resource.projectId(),
                resource.sensorId(),
                resource.unit(),
                resource.lastLecture(),
                resource.limit(),
                resource.location(),
                resource.timeLecture(),
                resource.state()
        );
    }
}