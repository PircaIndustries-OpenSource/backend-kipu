package com.kipu.backend.Logistics.application.commands;

import com.kipu.backend.Logistics.domain.model.valueobjects.RequestPriority;
import com.kipu.backend.Logistics.domain.model.valueobjects.SupplierId;
import com.kipu.backend.Logistics.domain.model.valueobjects.external.BudgetLineId;
import com.kipu.backend.Logistics.domain.model.valueobjects.external.UserId;

import java.time.Instant;
import java.util.List;

public record CreateMaterialRequestCommand(String projectId, Instant deadline, RequestPriority requestPriority,
                                           String deliveryLocation, BudgetLineId budgetLineId,
                                           String purpose, String additionalNotes, UserId requestedBy,
                                           SupplierId suggestedSupplierId,
                                           List<CreateMaterialRequestItemCommand> items) {
    public CreateMaterialRequestCommand {
        if (projectId == null || projectId.isBlank())
            throw new IllegalArgumentException("projectId cannot be null or blank");
        if (deadline == null)
            throw new IllegalArgumentException("deadline cannot be null");
        if (requestPriority == null)
            throw new IllegalArgumentException("requestPriority cannot be null");
        if (deliveryLocation == null || deliveryLocation.isBlank())
            throw new IllegalArgumentException("deliveryLocation cannot be null or blank");
        if (requestedBy == null)
            throw new IllegalArgumentException("requestedBy cannot be null");
        if (items == null || items.isEmpty())
            throw new IllegalArgumentException("items cannot be null or empty");
    }
}