package com.kipu.backend.teamworkers.interfaces.rest.resources;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateTeamWorkerResource(
        @NotBlank(message = "The DNI is required")
        @Size(min = 8, max = 15, message = "The DNI must be between 8 and 15 characters")
        String dni,

        @NotBlank(message = "The full name is required")
        @Size(min = 3, max = 100, message = "The full name must be between 3 and 100 characters")
        String fullName,

        @NotBlank(message = "The role is required")
        String role,

        @NotBlank(message = "The project ID cannot be blank")
        String projectId,

        @NotNull(message = "The machineries list cannot be null")
        List<@Valid TeamWorkerMachineryResource> machineries
) {}