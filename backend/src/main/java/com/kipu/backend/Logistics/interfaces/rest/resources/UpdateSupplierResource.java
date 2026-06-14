package com.kipu.backend.Logistics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Request resource used to update a supplier")
public record UpdateSupplierResource(
        @Schema(description = "Social reason", example = "CONSTRUCTORA DEL PERÚ S.A.", maxLength = 40, requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "supplier.error.socialReason.notBlank")
        @Size(min = 3, max = 40, message = "supplier.error.socialReason.size")
        String socialReason,

        @Schema(description = "Contact person name", example = "Juan Pérez", maxLength = 100)
        @Size(max = 100, message = "supplier.error.contact.size")
        String contact,

        @Schema(description = "Phone number", example = "+51999888777", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "supplier.error.phone.notBlank")
        @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "supplier.error.phone.pattern")
        String phone,

        @Schema(description = "Email address", example = "ventas@constructora.pe", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "supplier.error.email.notBlank")
        @Email(message = "supplier.error.email.pattern")
        @Size(max = 254, message = "supplier.error.email.size")
        String email,

        @Schema(description = "Payment terms", example = "Net 30 days", maxLength = 255)
        @Size(max = 255, message = "supplier.error.paymentTerms.size")
        String paymentTerms,

        @Schema(description = "Active status", example = "true")
        Boolean isActive
) {}
