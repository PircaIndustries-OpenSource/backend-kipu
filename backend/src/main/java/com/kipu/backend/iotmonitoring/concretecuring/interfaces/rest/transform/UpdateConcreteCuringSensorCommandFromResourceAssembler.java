package com.kipu.backend.iotmonitoring.concretecuring.interfaces.rest.transform;

import com.kipu.backend.iotmonitoring.concretecuring.domain.model.commands.UpdateConcreteCuringSensorCommand;
import com.kipu.backend.iotmonitoring.concretecuring.interfaces.rest.resources.UpdateConcreteCuringSensorResource;

public class UpdateConcreteCuringSensorCommandFromResourceAssembler {
    public static UpdateConcreteCuringSensorCommand toCommandFromResource (Long id, UpdateConcreteCuringSensorResource resource) {
        return new UpdateConcreteCuringSensorCommand(
                id,
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
