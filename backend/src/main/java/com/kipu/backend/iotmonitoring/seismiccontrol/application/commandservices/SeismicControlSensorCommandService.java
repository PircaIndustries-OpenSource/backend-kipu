package com.kipu.backend.iotmonitoring.seismiccontrol.application.commandservices;

import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.aggregates.SeismicControlSensor;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.commands.CreateSeismicControlSensorCommand;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.commands.UpdateSeismicControlSensorCommand;
import com.kipu.backend.iotmonitoring.seismiccontrol.domain.model.commands.DeleteSeismicControlSensorCommand;
import com.kipu.backend.shared.application.result.ApplicationError;
import com.kipu.backend.shared.application.result.Result;

/**
 * Seismic Control Sensor Command Service.
 * <p>
 * Defines the entry point boundary for executing orchestration and
 * state-mutation
 * operations within the seismic monitoring bounded context.
 * </p>
 */
public interface SeismicControlSensorCommandService {

    /**
     * Handles the creation of a new seismic control sensor node.
     *
     * @param command The {@link CreateSeismicControlSensorCommand} command.
     * @return A {@link Result} containing the fully initialized and persisted
     *         {@link SeismicControlSensor} on success,
     *         or an {@link ApplicationError} detailing business rule violations or
     *         mapping failures.
     */
    Result<SeismicControlSensor, ApplicationError> handle(CreateSeismicControlSensorCommand command);

    /**
     * Handles the update of an existing seismic control sensor node.
     *
     * @param command The {@link UpdateSeismicControlSensorCommand} command.
     * @return A {@link Result} containing the fully updated and persisted
     *         {@link SeismicControlSensor} on success,
     *         or an {@link ApplicationError} detailing business rule violations or
     *         mapping failures.
     */
    Result<SeismicControlSensor, ApplicationError> handle(UpdateSeismicControlSensorCommand command);

    /**
     * Handles the deletion of an existing seismic control sensor node.
     *
     * @param command The {@link DeleteSeismicControlSensorCommand} command.
     * @return A {@link Result} containing nothing on success,
     *         or an {@link ApplicationError} detailing business rule violations or
     *         mapping failures.
     */
    Result<Void, ApplicationError> handle(DeleteSeismicControlSensorCommand command);
}