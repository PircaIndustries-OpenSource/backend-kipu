package com.kipu.backend.Logistics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.List;

@Schema(description = "Request resource used to update a material request")
public record UpdateMaterialRequestResource(
        @Schema(description = "Deadline (ISO instant)", example = "2025-12-31T23:59:59Z", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "material.request.error.deadline.notBlank")
        Instant deadline,

        @Schema(description = "Request priority", example = "HIGH", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "material.request.error.requestPriority.notBlank")
        String requestPriority,

        @Schema(description = "Delivery location", example = "Site A - Warehouse 1", maxLength = 255, requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "material.request.error.deliveryLocation.notBlank")
        @Size(max = 255, message = "material.request.error.deliveryLocation.size")
        String deliveryLocation,

        @Schema(description = "Purpose of the request", example = "Construction of perimeter wall", maxLength = 500)
        @Size(max = 500, message = "material.request.error.purpose.size")
        String purpose,

        @Schema(description = "Additional notes", example = "Requires delivery before 2 PM", maxLength = 1000)
        @Size(max = 1000, message = "material.request.error.additionalNotes.size")
        String additionalNotes,

        @Schema(description = "List of items requested", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "material.request.error.items.notBlank")
        @Size(min = 1, message = "material.request.error.items.minSize")
        List<@Valid UpdateMaterialRequestItemResource> items
) {}
