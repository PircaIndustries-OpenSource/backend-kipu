package com.kipu.backend.Logistics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Response resource representing a supplier offer")
public record SupplierOfferResource(
        @Schema(description = "Persistent identifier", example = "1")
        Long id,

        @Schema(description = "Supplier identifier", example = "1")
        Integer supplierId,

        @Schema(description = "Material catalog identifier", example = "5")
        Integer materialCatalogId,

        @Schema(description = "Unit price", example = "25.50")
        BigDecimal unitPrice
) {}
