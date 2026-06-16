package com.kipu.backend.iotmonitoring.geolocalization.interfaces.rest.transform;

import com.kipu.backend.iotmonitoring.geolocalization.domain.model.commands.CreateGeolocalizationSensorCommand;
import com.kipu.backend.iotmonitoring.geolocalization.interfaces.rest.resources.CreateGeolocalizationSensorResource;

/**
 * Assembler to convert a CreateGeolocalizationSensorResource to a CreateGeolocalizationSensorCommand.
 */
public class CreateGeolocalizationSensorCommandFromResourceAssembler {

    /**
     * Converts a CreateGeolocalizationSensorResource to a CreateGeolocalizationSensorCommand.
     * * @param resource The {@link CreateGeolocalizationSensorResource} resource to convert.
     * @return The {@link CreateGeolocalizationSensorCommand} command.
     */
    public static CreateGeolocalizationSensorCommand toCommandFromResource(CreateGeolocalizationSensorResource resource) {
        return new CreateGeolocalizationSensorCommand(
                resource.projectId(),
                resource.sensorId(),
                resource.numberId(),
                resource.name(),
                resource.state(),
                resource.longitude(),
                resource.latitude()
        );
    }
}