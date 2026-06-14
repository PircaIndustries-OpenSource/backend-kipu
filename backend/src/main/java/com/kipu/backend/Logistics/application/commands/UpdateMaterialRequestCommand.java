package com.kipu.backend.Logistics.application.commands;

import com.kipu.backend.Logistics.domain.model.valueobjects.RequestPriority;

import java.time.Instant;
import java.util.List;

public record UpdateMaterialRequestCommand(
        Instant deadline,
        RequestPriority requestPriority,
        String deliveryLocation,
        String purpose,
        String additionalNotes,
        List<UpdateMaterialRequestItemCommand> items
) {
    public UpdateMaterialRequestCommand {
        // Null fields are allowed (PATCH semantics)
        // Validation is handled at the controller level via @Valid on the resource
    }
}
