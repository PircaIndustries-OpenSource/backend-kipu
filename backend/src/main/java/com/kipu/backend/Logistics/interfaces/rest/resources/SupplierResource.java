package com.kipu.backend.Logistics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response resource representing a supplier")
public record SupplierResource(
        @Schema(description = "Persistent identifier", example = "1")
        Long id,

        @Schema(description = "RUC", example = "20123456789")
        String ruc,

        @Schema(description = "Social reason", example = "CONSTRUCTORA DEL PERÚ S.A.")
        String socialReason,

        @Schema(description = "Contact person name", example = "Juan Pérez")
        String contact,

        @Schema(description = "Phone number", example = "+51999888777")
        String phone,

        @Schema(description = "Email address", example = "ventas@constructora.pe")
        String email,

        @Schema(description = "Payment terms", example = "Net 30 days")
        String paymentTerms,

        @Schema(description = "Active status", example = "true")
        Boolean isActive
) {}