package com.kipu.backend.Logistics.application.commands;

import com.kipu.backend.Logistics.domain.model.valueobjects.MaterialCatalogId;
import com.kipu.backend.Logistics.domain.model.valueobjects.Quantity;
import com.kipu.backend.Logistics.domain.model.valueobjects.WarehouseLocation;
import com.kipu.backend.Logistics.domain.model.valueobjects.external.ProjectId;

/**
 * Command for creating a material inventory.
 *
 * @param projectId         the project ID value object
 * @param materialCatalogId the material catalog ID value object
 * @param currentStock      the current stock quantity
 * @param minimumStock      the minimum stock quantity (can be null, defaults to 0)
 * @param location          the warehouse location value object
 */
public record CreateMaterialInventoryCommand(ProjectId projectId, MaterialCatalogId materialCatalogId,
                                             Quantity currentStock, Quantity minimumStock,
                                             WarehouseLocation location) {
    /**
     * Validates the command.
     *
     * @throws IllegalArgumentException if any required parameter is null
     */
    public CreateMaterialInventoryCommand {
        if (projectId == null)
            throw new IllegalArgumentException("projectId cannot be null");
        if (materialCatalogId == null)
            throw new IllegalArgumentException("materialCatalogId cannot be null");
        if (currentStock == null)
            throw new IllegalArgumentException("currentStock cannot be null");
        // minimumStock can be null (will be set to Quantity(0) in aggregate)
        if (location == null)
            throw new IllegalArgumentException("location cannot be null");
    }
}