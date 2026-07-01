package com.kipu.backend.Logistics.machinery.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request resource used to create a machinery entry")
public record CreateMachineryResource(
        @Schema(description = "Machinery name/description", example = "Excavadora CAT 320", maxLength = 200, requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "machinery.error.name.notBlank")
        @Size(max = 200, message = "machinery.error.name.size")
        String name,

        @Schema(description = "Worker assigned (DNI - FullName)", example = "12345678 - Juan Pérez", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String assignedTo,

        @Schema(description = "Assignment detail/purpose", example = "Asignado para excavación en zona A", maxLength = 500, requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "machinery.error.assignmentDetail.notBlank")
        @Size(max = 500, message = "machinery.error.assignmentDetail.size")
        String assignmentDetail
) {}
