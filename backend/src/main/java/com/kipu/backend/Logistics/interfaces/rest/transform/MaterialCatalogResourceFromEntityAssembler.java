package com.kipu.backend.Logistics.interfaces.rest.transform;

import com.kipu.backend.Logistics.domain.model.aggregates.MaterialCatalog;
import com.kipu.backend.Logistics.interfaces.rest.resources.MaterialCatalogResource;

public class MaterialCatalogResourceFromEntityAssembler {

    public static MaterialCatalogResource toResourceFromEntity(MaterialCatalog entity) {
        return new MaterialCatalogResource(
                entity.getId(),
                entity.getName().value(),
                entity.getCategoryId().value(),
                entity.getMeasureUnit().name()
        );
    }
}