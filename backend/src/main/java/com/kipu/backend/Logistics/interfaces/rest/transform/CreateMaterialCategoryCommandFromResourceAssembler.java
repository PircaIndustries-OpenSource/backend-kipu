package com.kipu.backend.Logistics.interfaces.rest.transform;

import com.kipu.backend.Logistics.application.commands.CreateMaterialCategoryCommand;
import com.kipu.backend.Logistics.interfaces.rest.resources.CreateMaterialCategoryResource;

import static com.kipu.backend.Logistics.interfaces.rest.transform.LogisticsValueObjectFromStringAssembler.toNameFromString;

public class CreateMaterialCategoryCommandFromResourceAssembler {

    public static CreateMaterialCategoryCommand toCommandFromResource(CreateMaterialCategoryResource resource) {
        return new CreateMaterialCategoryCommand(
                toNameFromString(resource.name()),
                resource.description(),
                resource.isActive()
        );
    }
}