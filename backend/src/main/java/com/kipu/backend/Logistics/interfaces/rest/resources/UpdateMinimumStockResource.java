package com.kipu.backend.Logistics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Request resource used to update the minimum stock of an inventory item")
public record UpdateMinimumStockResource(
        @Schema(description = "New minimum stock value", example = "50", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "material.inventory.error.minimumStock.notNull")
        @PositiveOrZero(message = "material.inventory.error.minimumStock.invalidValue")
        Integer minimumStock
) {}
