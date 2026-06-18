package com.kipu.backend.teamworkers.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Represents the detailed information of a team worker")
public record TeamWorkerResource(
        @Schema(description = "Unique identifier of the team worker", example = "wrk-98765")
        String id,

        @Schema(description = "National Identity Document (DNI) of the worker", example = "72345678")
        String dni,

        @Schema(description = "Full name of the worker", example = "Carlos Ramirez")
        String fullName,

        @Schema(description = "Assigned role of the worker", example = "Operador de Maquinaria")
        String role,

        @Schema(description = "Indicates if the worker is currently active", example = "true")
        boolean isActive,

        @Schema(description = "Unique identifier of the associated project", example = "proj-01")
        String projectId,

        @Schema(description = "List of machineries currently assigned to the worker")
        List<TeamWorkerMachineryResource> machineries
) {}