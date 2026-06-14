package com.kipu.backend.Logistics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.List;

@Schema(description = "Response resource representing a material request")
public record MaterialRequestResource(
        @Schema(description = "Persistent identifier", example = "1")
        Long id,

        @Schema(description = "Deadline", example = "2025-12-31T23:59:59Z")
        Instant deadline,

        @Schema(description = "Request status", example = "PENDING")
        String requestStatus,

        @Schema(description = "Request priority", example = "HIGH")
        String requestPriority,

        @Schema(description = "Delivery location", example = "Site A - Warehouse 1")
        String deliveryLocation,

        @Schema(description = "Budget line identifier", example = "100")
        Integer budgetLineId,

        @Schema(description = "Purpose", example = "Construction of perimeter wall")
        String purpose,

        @Schema(description = "Additional notes", example = "Requires delivery before 2 PM")
        String additionalNotes,

        @Schema(description = "User who requested", example = "42")
        Integer requestedBy,

        @Schema(description = "List of items")
        List<MaterialRequestItemResource> items
) {}