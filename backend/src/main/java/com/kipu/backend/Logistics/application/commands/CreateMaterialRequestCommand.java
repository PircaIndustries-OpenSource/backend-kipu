package com.kipu.backend.Logistics.application.commands;

import com.kipu.backend.Logistics.domain.model.valueobjects.RequestPriority;
import com.kipu.backend.Logistics.domain.model.valueobjects.external.BudgetLineId;
import com.kipu.backend.Logistics.domain.model.valueobjects.external.UserId;

import java.time.Instant;
import java.util.List;

/**
 * Command for creating a material request with its items.
 *
 * @param deadline          the deadline instant
 * @param requestPriority   the priority enum
 * @param deliveryLocation  the delivery location string
 * @param budgetLineId      the budget line ID value object
 * @param purpose           the purpose description (can be null)
 * @param additionalNotes   additional notes (can be null)
 * @param requestedBy       the user ID who requested
 * @param items             list of item commands (non-null, non-empty)
 */
public record CreateMaterialRequestCommand(Instant deadline, RequestPriority requestPriority,
                                           String deliveryLocation, BudgetLineId budgetLineId,
                                           String purpose, String additionalNotes, UserId requestedBy,
                                           List<CreateMaterialRequestItemCommand> items) {
    /**
     * Validates the command.
     *
     * @throws IllegalArgumentException if any required parameter is null,
     *                                  or if items list is null or empty
     */
    public CreateMaterialRequestCommand {
        if (deadline == null)
            throw new IllegalArgumentException("deadline cannot be null");
        if (requestPriority == null)
            throw new IllegalArgumentException("requestPriority cannot be null");
        if (deliveryLocation == null || deliveryLocation.isBlank())
            throw new IllegalArgumentException("deliveryLocation cannot be null or blank");
        // budgetLineId is optional; null is allowed
        if (requestedBy == null)
            throw new IllegalArgumentException("requestedBy cannot be null");
        if (items == null || items.isEmpty())
            throw new IllegalArgumentException("items cannot be null or empty");
    }
}