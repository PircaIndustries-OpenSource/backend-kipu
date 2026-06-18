package com.kipu.backend.Logistics.interfaces.rest.transform;

import com.kipu.backend.Logistics.application.commands.CreateSupplierOfferCommand;
import com.kipu.backend.Logistics.interfaces.rest.resources.CreateSupplierOfferResource;

import static com.kipu.backend.Logistics.interfaces.rest.transform.LogisticsValueObjectFromStringAssembler.*;

public class CreateSupplierOfferCommandFromResourceAssembler {

    public static CreateSupplierOfferCommand toCommandFromResource(CreateSupplierOfferResource resource) {
        return new CreateSupplierOfferCommand(
                toSupplierIdFromInteger(resource.supplierId()),
                toMaterialCatalogIdFromInteger(resource.materialCatalogId()),
                resource.unitPrice()
        );
    }
}
