package com.kipu.backend.Logistics.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request resource used to update the status of a material request")
public record UpdateMaterialRequestStatusResource(
        @Schema(description = "New status (PENDING, ACCEPTED, REFUSED, CANCELLED)", example = "ACCEPTED", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "material.request.error.requestStatus.notBlank")
        String status
) {}
