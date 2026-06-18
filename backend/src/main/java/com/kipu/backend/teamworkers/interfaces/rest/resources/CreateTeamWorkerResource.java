package com.kipu.backend.teamworkers.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(description = "Payload to register a new team worker in a project")
public record CreateTeamWorkerResource(
        @Schema(description = "National Identity Document (DNI) of the worker", example = "72345678")
        @NotBlank(message = "{worker.validation.dniRequired}")
        @Size(min = 8, max = 8, message = "{worker.validation.dniSize}")
        String dni,

        @Schema(description = "Full name of the worker", example = "Carlos Ramirez")
        @NotBlank(message = "{worker.validation.fullNameRequired}")
        @Size(min = 3, max = 100, message = "{worker.validation.fullNameSize}")
        String fullName,

        @Schema(description = "Assigned role of the worker within the project", example = "Operador de Maquinaria")
        @NotBlank(message = "{worker.validation.roleRequired}")
        String role,

        @Schema(description = "Unique identifier of the project the worker belongs to", example = "proj-01")
        @NotBlank(message = "{worker.validation.projectIdRequired}")
        String projectId,

        @Schema(description = "List of machineries assigned to the worker at creation")
        @NotNull(message = "{worker.validation.machineriesNotNull}")
        List<@Valid TeamWorkerMachineryResource> machineries
) {}