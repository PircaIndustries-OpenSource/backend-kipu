package com.kipu.backend.iotmonitoring.geolocalization.application.commandservices;

import com.kipu.backend.iotmonitoring.geolocalization.domain.model.aggregates.GeolocalizationSensor;
import com.kipu.backend.iotmonitoring.geolocalization.domain.model.commands.CreateGeolocalizationSensorCommand;
import com.kipu.backend.shared.application.result.ApplicationError;
import com.kipu.backend.shared.application.result.Result;

/**
 * Geolocalization Sensor Command Service.
 */
public interface GeolocalizationSensorCommandService {

    /**
     * Handle Create Geolocalization Sensor Command.
     *
     * @param command The {@link CreateGeolocalizationSensorCommand} Command
     * @return A {@link Result} containing the created {@link GeolocalizationSensor} on success,
     * or an {@link ApplicationError} on failure (validation or business rule violation)
     */
    Result<GeolocalizationSensor, ApplicationError> handle(CreateGeolocalizationSensorCommand command);
}