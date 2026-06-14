package com.kipu.backend.Logistics.application.commands;

import com.kipu.backend.Logistics.domain.model.valueobjects.Email;
import com.kipu.backend.Logistics.domain.model.valueobjects.Phone;
import com.kipu.backend.Logistics.domain.model.valueobjects.SocialReason;

public record UpdateSupplierCommand(
        SocialReason socialReason,
        String contact,
        Phone phone,
        Email email,
        String paymentTerms,
        Boolean isActive
) {
    public UpdateSupplierCommand {
        // Null fields are allowed (PATCH semantics)
        // Validation is handled at the controller level via @Valid on the resource
    }
}
