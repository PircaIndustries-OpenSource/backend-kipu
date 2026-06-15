package com.kipu.backend.Logistics.application.commandservices;

import com.kipu.backend.Logistics.application.commands.CreateMaterialInventoryCommand;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialInventory;
import com.kipu.backend.shared.application.result.Result;

public interface MaterialInventoryCommandService {
    /**
     * Handles creation of a material inventory.
     *
     * @param command create command containing projectId, materialCatalogId, currentStock, minimumStock and location
     * @return success when the material inventory is created, failure when an inventory for the same project and material already exists
     */
    Result<MaterialInventory, MaterialInventoryCommandFailure> handle(CreateMaterialInventoryCommand command);
}