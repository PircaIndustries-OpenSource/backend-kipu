package com.kipu.backend.teamworkers.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record TeamWorkerMachineryResource(
        @NotBlank(message = "The machinery ID is required")
        String machineryId,

        @NotBlank(message = "The machinery full name is required")
        String fullName
) {}