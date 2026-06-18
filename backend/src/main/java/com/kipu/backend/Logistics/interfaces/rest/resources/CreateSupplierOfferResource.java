package com.kipu.backend.Logistics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Schema(description = "Request resource used to create a supplier offer")
public record CreateSupplierOfferResource(
        @Schema(description = "Supplier identifier", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "supplier.offer.error.supplierId.notBlank")
        @Positive(message = "supplier.offer.error.supplierId.invalidValue")
        Integer supplierId,

        @Schema(description = "Material catalog identifier", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "supplier.offer.error.materialCatalogId.notBlank")
        @Positive(message = "supplier.offer.error.materialCatalogId.invalidValue")
        Integer materialCatalogId,

        @Schema(description = "Unit price", example = "25.50", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "supplier.offer.error.unitPrice.notBlank")
        @Positive(message = "supplier.offer.error.unitPrice.invalidValue")
        BigDecimal unitPrice
) {}
