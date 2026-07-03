package com.kipu.backend.Logistics.interfaces.rest.transform;

import com.kipu.backend.Logistics.application.commands.UpdateMaterialWasteCommand;
import com.kipu.backend.Logistics.domain.model.valueobjects.MaterialCatalogId;
import com.kipu.backend.Logistics.domain.model.valueobjects.Quantity;
import com.kipu.backend.Logistics.domain.model.valueobjects.WasteClassificationType;
import com.kipu.backend.Logistics.domain.model.valueobjects.WasteUnit;
import com.kipu.backend.Logistics.domain.model.valueobjects.external.ProjectId;
import com.kipu.backend.Logistics.interfaces.rest.resources.UpdateMaterialWasteResource;

public class UpdateMaterialWasteCommandFromResourceAssembler {

    public static UpdateMaterialWasteCommand toCommandFromResource(Long id, UpdateMaterialWasteResource resource) {
        return new UpdateMaterialWasteCommand(
                id,
                new ProjectId(resource.projectId()),
                new MaterialCatalogId(resource.materialCatalogId()),
                new Quantity(resource.quantity()),
                new WasteUnit(resource.unit()),
                WasteClassificationType.valueOf(resource.classificationType().toUpperCase()),
                resource.date(),
                resource.description(),
                resource.reportedBy(),
                resource.photoUrl() != null ? resource.photoUrl() : ""
        );
    }
}
