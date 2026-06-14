package com.kipu.backend.Logistics.interfaces.rest.transform;

import com.kipu.backend.Logistics.application.commands.CreateMaterialInventoryCommand;
import com.kipu.backend.Logistics.interfaces.rest.resources.CreateMaterialInventoryResource;

import static com.kipu.backend.Logistics.interfaces.rest.transform.LogisticsValueObjectFromStringAssembler.*;

public class CreateMaterialInventoryCommandFromResourceAssembler {

    public static CreateMaterialInventoryCommand toCommandFromResource(CreateMaterialInventoryResource resource) {
        return new CreateMaterialInventoryCommand(
                toProjectIdFromInteger(resource.projectId()),
                toMaterialCatalogIdFromInteger(resource.materialCatalogId()),
                toQuantityFromInteger(resource.currentStock()),
                resource.minimumStock() != null ? toQuantityFromInteger(resource.minimumStock()) : null,
                toWarehouseLocationFromString(resource.location())
        );
    }
}