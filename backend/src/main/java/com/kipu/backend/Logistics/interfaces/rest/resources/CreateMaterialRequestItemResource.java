package com.kipu.backend.Logistics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Schema(description = "Request resource for an item inside a material request")
public record CreateMaterialRequestItemResource(
        @Schema(description = "Material catalog identifier", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "material.request.item.error.materialCatalogId.notBlank")
        @Positive(message = "material.request.item.error.materialCatalogId.invalidValue")
        Integer materialCatalogId,

        @Schema(description = "Supplier identifier", example = "12", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "material.request.item.error.supplierId.notBlank")
        @Positive(message = "material.request.item.error.supplierId.invalidValue")
        Integer supplierId,

        @Schema(description = "Requested quantity", example = "50", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "material.request.item.error.quantity.notBlank")
        @Positive(message = "material.request.item.error.quantity.invalidValue")
        BigDecimal quantity,

        @Schema(description = "Unit price", example = "25.50", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "material.request.item.error.unitPrice.notBlank")
        @Positive(message = "material.request.item.error.unitPrice.invalidValue")
        BigDecimal unitPrice
) {}