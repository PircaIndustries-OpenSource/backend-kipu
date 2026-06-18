package com.kipu.backend.Logistics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

@Schema(description = "Request resource used to update a supplier offer")
public record UpdateSupplierOfferResource(
        @Schema(description = "Unit price", example = "30.00", requiredMode = Schema.RequiredMode.REQUIRED)
        @Positive(message = "supplier.offer.error.unitPrice.invalidValue")
        BigDecimal unitPrice
) {}
