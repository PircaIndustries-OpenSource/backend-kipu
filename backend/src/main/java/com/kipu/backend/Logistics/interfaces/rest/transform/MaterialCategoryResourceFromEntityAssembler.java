package com.kipu.backend.Logistics.interfaces.rest.transform;

import com.kipu.backend.Logistics.domain.model.aggregates.MaterialCategory;
import com.kipu.backend.Logistics.interfaces.rest.resources.MaterialCategoryResource;

public class MaterialCategoryResourceFromEntityAssembler {

    public static MaterialCategoryResource toResourceFromEntity(MaterialCategory entity) {
        return new MaterialCategoryResource(
                entity.getId(),
                entity.getName().value(),
                entity.getDescription(),
                entity.getIsActive()
        );
    }
}