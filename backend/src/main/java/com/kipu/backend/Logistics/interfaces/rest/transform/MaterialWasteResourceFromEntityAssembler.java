package com.kipu.backend.Logistics.interfaces.rest.transform;

import com.kipu.backend.Logistics.domain.model.aggregates.MaterialWaste;
import com.kipu.backend.Logistics.interfaces.rest.resources.MaterialWasteResource;

public class MaterialWasteResourceFromEntityAssembler {

    public static MaterialWasteResource toResourceFromEntity(MaterialWaste entity) {
        return new MaterialWasteResource(
                entity.getId(),
                entity.getProjectId().value(),
                entity.getMaterialCatalogId().value(),
                entity.getQuantity().value(),
                entity.getUnit().value(),
                entity.getClassificationType().name(),
                entity.getDate(),
                entity.getDescription(),
                entity.getReportedBy(),
                entity.getPhotoUrl()
        );
    }
}
