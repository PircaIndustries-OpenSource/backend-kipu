package com.kipu.backend.Logistics.interfaces.rest.transform;

import com.kipu.backend.Logistics.application.commands.CreateMaterialCatalogCommand;
import com.kipu.backend.Logistics.interfaces.rest.resources.CreateMaterialCatalogResource;

import static com.kipu.backend.Logistics.interfaces.rest.transform.LogisticsValueObjectFromStringAssembler.*;

public class CreateMaterialCatalogCommandFromResourceAssembler {

    public static CreateMaterialCatalogCommand toCommandFromResource(CreateMaterialCatalogResource resource) {
        return new CreateMaterialCatalogCommand(
                toNameFromString(resource.name()),
                toCategoryIdFromInteger(resource.categoryId()),
                toMeasureUnitFromString(resource.measureUnit())
        );
    }
}