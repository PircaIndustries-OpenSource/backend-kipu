package com.kipu.backend.Logistics.interfaces.rest.transform;

import com.kipu.backend.Logistics.application.commands.UpdateSupplierOfferCommand;
import com.kipu.backend.Logistics.interfaces.rest.resources.UpdateSupplierOfferResource;

public class UpdateSupplierOfferCommandFromResourceAssembler {

    public static UpdateSupplierOfferCommand toCommandFromResource(UpdateSupplierOfferResource resource) {
        return new UpdateSupplierOfferCommand(resource.unitPrice());
    }
}
