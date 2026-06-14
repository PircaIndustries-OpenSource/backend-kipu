package com.kipu.backend.Logistics.interfaces.rest.transform;

import com.kipu.backend.Logistics.application.commands.CreateSupplierCommand;
import com.kipu.backend.Logistics.interfaces.rest.resources.CreateSupplierResource;

import static com.kipu.backend.Logistics.interfaces.rest.transform.LogisticsValueObjectFromStringAssembler.*;

public class CreateSupplierCommandFromResourceAssembler {

    public static CreateSupplierCommand toCommandFromResource(CreateSupplierResource resource) {
        return new CreateSupplierCommand(
                toRucFromString(resource.ruc()),
                toSocialReasonFromString(resource.socialReason()),
                resource.contact(),
                toPhoneFromString(resource.phone()),
                toEmailFromString(resource.email()),
                resource.paymentTerms(),
                resource.isActive()
        );
    }
}