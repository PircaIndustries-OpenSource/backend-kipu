package com.kipu.backend.iotmonitoring.geolocalization.interfaces.rest.transform;

import com.kipu.backend.iotmonitoring.geolocalization.domain.model.commands.UpdateGeolocalizationSensorCommand;
import com.kipu.backend.iotmonitoring.geolocalization.interfaces.rest.resources.UpdateGeolocalizationSensorResource;

public class UpdateGeolocalizationSensorCommandFromResourceAssembler {
    public static UpdateGeolocalizationSensorCommand toCommandFromResource(Long id, UpdateGeolocalizationSensorResource resource) {
        return new UpdateGeolocalizationSensorCommand(
                id,
                resource.sensorId(),
                resource.numberId(),
                resource.name(),
                resource.state(),
                resource.longitude(),
                resource.latitude()
        );
    }
}
