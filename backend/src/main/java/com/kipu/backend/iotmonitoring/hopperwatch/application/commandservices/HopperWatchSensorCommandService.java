package com.kipu.backend.iotmonitoring.hopperwatch.application.commandservices;

import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.aggregates.HopperWatchSensor;
import com.kipu.backend.iotmonitoring.hopperwatch.domain.model.commands.CreateHopperWatchSensorCommand;
import com.kipu.backend.shared.application.result.ApplicationError;
import com.kipu.backend.shared.application.result.Result;

/**
 * Hopper Watch Command Service.
 */
public interface HopperWatchSensorCommandService {

    /**
     * Handle Create Hopper Watch Command.
     *
     * @param command The {@link CreateHopperWatchSensorCommand} Command
     * @return A {@link Result} containing the created {@link HopperWatchSensor} on success,
     * or an {@link ApplicationError} on failure (validation or business rule violation)
     */
    Result<HopperWatchSensor, ApplicationError> handle(CreateHopperWatchSensorCommand command);
}