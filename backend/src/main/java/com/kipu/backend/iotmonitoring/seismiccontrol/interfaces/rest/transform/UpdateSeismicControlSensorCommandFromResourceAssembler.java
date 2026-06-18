package com.kipu.backend.iotmonitoring.seismiccontrol.interfaces.rest.transform;

import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.commands.UpdateSeismicControlSensorCommand;
import com.kipu.backend.iotmonitoring.seismiccontrol.interfaces.rest.resources.UpdateSeismicControlSensorResource;

public class UpdateSeismicControlSensorCommandFromResourceAssembler {
    public static UpdateSeismicControlSensorCommand toCommandFromResource(Long id, UpdateSeismicControlSensorResource resource) {
        return new UpdateSeismicControlSensorCommand(
                id,
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
