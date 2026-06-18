package com.kipu.backend.teamworkers.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Payload to assign a specific machinery to an existing team worker")
public record AssignMachineryResource(
        @Schema(description = "Unique identifier of the machinery to assign", example = "maq-12345")
        @NotBlank(message = "worker.validation.machineryIdRequired")
        String machineryId,

        @Schema(description = "Full name or description of the machinery", example = "Caterpillar Excavator 320")
        @NotBlank(message = "worker.validation.machineryFullNameRequired")
        String fullName
) {}