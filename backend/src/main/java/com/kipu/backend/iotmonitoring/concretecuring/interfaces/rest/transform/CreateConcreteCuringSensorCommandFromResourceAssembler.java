package com.kipu.backend.iotmonitoring.concretecuring.interfaces.rest.transform;

import com.kipu.backend.iotmonitoring.concretecuring.domain.model.commands.CreateConcreteCuringSensorCommand;
import com.kipu.backend.iotmonitoring.concretecuring.interfaces.rest.resources.CreateConcreteCuringSensorResource;

/**
 * Assembler to convert a CreateConcreteCuringSensorResource to a CreateConcreteCuringSensorCommand.
 */
public class CreateConcreteCuringSensorCommandFromResourceAssembler {

    /**
     * Converts a CreateConcreteCuringSensorResource to a CreateConcreteCuringSensorCommand.
     *
     * @param resource The {@link CreateConcreteCuringSensorResource} resource to convert.
     * @return The {@link CreateConcreteCuringSensorCommand} command.
     */
    public static CreateConcreteCuringSensorCommand toCommandFromResource(CreateConcreteCuringSensorResource resource) {
        return new CreateConcreteCuringSensorCommand(
                resource.projectId(),
                resource.sensorId(),
                resource.state(),
                resource.location(),
                resource.temperatureReading(),
                resource.temperatureUnit(),
                resource.humidityPercentage(),
                resource.temperatureLimit()
        );
    }
}