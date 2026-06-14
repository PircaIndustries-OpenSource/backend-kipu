package com.kipu.backend.Logistics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Response resource representing an item inside a material request")
public record MaterialRequestItemResource(
        @Schema(description = "Item identifier", example = "101")
        Long id,

        @Schema(description = "Material catalog identifier", example = "5")
        Integer materialCatalogId,

        @Schema(description = "Supplier identifier", example = "12")
        Integer supplierId,

        @Schema(description = "Requested quantity", example = "50")
        BigDecimal quantity,

        @Schema(description = "Unit price", example = "25.50")
        BigDecimal unitPrice,

        @Schema(description = "Total price (quantity * unitPrice)", example = "1275.00")
        BigDecimal totalPrice
) {}