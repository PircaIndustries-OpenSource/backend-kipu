package com.kipu.backend.Logistics.application.commandservices;

import com.kipu.backend.Logistics.application.commands.CreateMaterialRequestCommand;
import com.kipu.backend.Logistics.application.commands.UpdateMaterialRequestCommand;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialRequest;
import com.kipu.backend.shared.application.result.Result;

public interface MaterialRequestCommandService {
    /**
     * Handles creation of a material request.
     *
     * @param command create command containing deadline, priority, location, budgetLineId, items, etc.
     * @return success when the material request is created, failure when budget is insufficient
     */
    Result<MaterialRequest, MaterialRequestCommandFailure> handle(CreateMaterialRequestCommand command);

    /**
     * Handles full update (PUT) of a material request.
     *
     * @param id      the request ID
     * @param command update command with all mutable fields
     * @return success when updated, failure when not found
     */
    Result<MaterialRequest, MaterialRequestCommandFailure> handleUpdate(Long id, UpdateMaterialRequestCommand command);

    /**
     * Handles partial update (PATCH) of a material request.
     *
     * @param id      the request ID
     * @param command update command with fields to patch (null = keep existing)
     * @return success when updated, failure when not found
     */
    Result<MaterialRequest, MaterialRequestCommandFailure> handlePatch(Long id, UpdateMaterialRequestCommand command);

    /**
     * Handles status update of a material request.
     *
     * @param id     the request ID
     * @param status the new status
     * @return success when updated, failure when not found
     */
    Result<MaterialRequest, MaterialRequestCommandFailure> handleStatusUpdate(Long id, String status);
}