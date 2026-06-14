package com.kipu.backend.Logistics.interfaces.rest.transform;

import com.kipu.backend.Logistics.application.commands.UpdateSupplierCommand;
import com.kipu.backend.Logistics.interfaces.rest.resources.UpdateSupplierResource;

import static com.kipu.backend.Logistics.interfaces.rest.transform.LogisticsValueObjectFromStringAssembler.*;

public class UpdateSupplierCommandFromResourceAssembler {

    public static UpdateSupplierCommand toCommandFromResource(UpdateSupplierResource resource) {
        return new UpdateSupplierCommand(
                toSocialReasonFromString(resource.socialReason()),
                resource.contact(),
                toPhoneFromString(resource.phone()),
                toEmailFromString(resource.email()),
                resource.paymentTerms(),
                resource.isActive()
        );
    }

    public static UpdateSupplierCommand toPatchCommandFromResource(UpdateSupplierResource resource) {
        return new UpdateSupplierCommand(
                resource.socialReason() != null ? toSocialReasonFromString(resource.socialReason()) : null,
                resource.contact(),
                resource.phone() != null ? toPhoneFromString(resource.phone()) : null,
                resource.email() != null ? toEmailFromString(resource.email()) : null,
                resource.paymentTerms(),
                resource.isActive()
        );
    }
}
