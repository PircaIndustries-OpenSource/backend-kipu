package com.kipu.backend.Logistics.machinerycatalog.interfaces.rest.transform;

import com.kipu.backend.Logistics.machinerycatalog.domain.model.aggregates.MachineryCatalog;
import com.kipu.backend.Logistics.machinerycatalog.interfaces.rest.resources.MachineryCatalogResource;

public class MachineryCatalogResourceFromEntityAssembler {

    public static MachineryCatalogResource toResource(MachineryCatalog catalog) {
        return new MachineryCatalogResource(
                catalog.getId(),
                catalog.getName().value(),
                catalog.getBrand(),
                catalog.getModel(),
                catalog.getSerialNumber(),
                catalog.getAcquisitionDate()
        );
    }
}
