package com.kipu.backend.Logistics.interfaces.rest.transform;

import com.kipu.backend.Logistics.domain.model.aggregates.MaterialInventory;
import com.kipu.backend.Logistics.interfaces.rest.resources.MaterialInventoryResource;

public class MaterialInventoryResourceFromEntityAssembler {

    public static MaterialInventoryResource toResourceFromEntity(MaterialInventory entity) {
        return new MaterialInventoryResource(
                entity.getId(),
                entity.getProjectId().value(),
                entity.getMaterialCatalogId().value(),
                entity.getCurrentStock().value(),
                entity.getMinimumStock().value(),
                entity.getLocation().value()
        );
    }
}