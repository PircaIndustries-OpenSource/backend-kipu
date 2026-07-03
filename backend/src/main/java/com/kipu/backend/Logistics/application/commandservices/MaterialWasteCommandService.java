package com.kipu.backend.Logistics.application.commandservices;

import com.kipu.backend.Logistics.application.commands.CreateMaterialWasteCommand;
import com.kipu.backend.Logistics.application.commands.DeleteMaterialWasteCommand;
import com.kipu.backend.Logistics.application.commands.UpdateMaterialWasteCommand;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialWaste;
import com.kipu.backend.shared.application.result.Result;

public interface MaterialWasteCommandService {

    /**
     * Handles creation of a material waste record.
     *
     * @param command create command with all required waste fields
     * @return success containing the persisted waste, or a failure
     */
    Result<MaterialWaste, MaterialWasteCommandFailure> handle(CreateMaterialWasteCommand command);

    /**
     * Handles update of an existing material waste record.
     *
     * @param command update command including the target waste ID
     * @return success containing the updated waste, or a NotFound failure
     */
    Result<MaterialWaste, MaterialWasteCommandFailure> handle(UpdateMaterialWasteCommand command);

    /**
     * Handles deletion of a material waste record.
     *
     * @param command delete command containing the target waste ID
     * @return success with the deleted ID, or a NotFound failure
     */
    Result<Long, MaterialWasteCommandFailure> handle(DeleteMaterialWasteCommand command);
}
