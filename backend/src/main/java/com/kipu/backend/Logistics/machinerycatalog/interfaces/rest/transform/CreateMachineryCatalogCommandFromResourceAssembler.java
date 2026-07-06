package com.kipu.backend.Logistics.machinerycatalog.interfaces.rest.transform;

import com.kipu.backend.Logistics.machinerycatalog.application.commands.CreateMachineryCatalogCommand;
import com.kipu.backend.Logistics.machinerycatalog.domain.model.valueobjects.CatalogName;
import com.kipu.backend.Logistics.machinerycatalog.interfaces.rest.resources.CreateMachineryCatalogResource;

public class CreateMachineryCatalogCommandFromResourceAssembler {

    public static CreateMachineryCatalogCommand toCommandFromResource(CreateMachineryCatalogResource resource) {
        return new CreateMachineryCatalogCommand(
                new CatalogName(resource.name()),
                resource.brand(),
                resource.model(),
                resource.serialNumber(),
                resource.acquisitionDate()
        );
    }
}
