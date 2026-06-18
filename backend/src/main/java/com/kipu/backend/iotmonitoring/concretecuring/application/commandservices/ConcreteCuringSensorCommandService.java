package com.kipu.backend.iotmonitoring.concretecuring.application.commandservices;

import com.kipu.backend.iotmonitoring.concretecuring.domain.model.aggregates.ConcreteCuringSensor;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.commands.CreateConcreteCuringSensorCommand;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.commands.DeleteConcreteCuringSensorCommand;
import com.kipu.backend.iotmonitoring.concretecuring.domain.model.commands.UpdateConcreteCuringSensorCommand;
import com.kipu.backend.shared.application.result.ApplicationError;
import com.kipu.backend.shared.application.result.Result;

/**
 * Concrete Curing Command Service
 */
public interface ConcreteCuringSensorCommandService {

    /**
     * Handle Create Concrete Curing Command
     *
     * @param command The {@link CreateConcreteCuringSensorCommand} Command
     * @return A {@link Result} containing the created {@link ConcreteCuringSensor} on success,
     * or an {@link ApplicationError} on failure (validation or business rule violation)
     */
    Result<ConcreteCuringSensor, ApplicationError> handle(CreateConcreteCuringSensorCommand command);

    Result<ConcreteCuringSensor, ApplicationError> handle(UpdateConcreteCuringSensorCommand command);

    Result<Void, ApplicationError> handle(DeleteConcreteCuringSensorCommand command);
}