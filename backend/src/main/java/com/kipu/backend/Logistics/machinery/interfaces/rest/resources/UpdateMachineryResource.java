package com.kipu.backend.Logistics.machinery.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request resource used to update a machinery entry (partial update)")
public record UpdateMachineryResource(
        @Schema(description = "Machinery name/description", example = "Excavadora CAT 320", maxLength = 200)
        String name,

        @Schema(description = "Current status", example = "AVAILABLE")
        String status,

        @Schema(description = "Worker assigned (DNI - FullName)", example = "12345678 - Juan Pérez")
        String assignedTo,

        @Schema(description = "Team worker ID assigned", example = "wrk-a1b2c3d4e5f6")
        String assignedWorkerId,

        @Schema(description = "Maintenance hours accumulated", example = "120")
        String maintenanceHours,

        @Schema(description = "Assignment detail/purpose", example = "Asignado para excavación en zona A", maxLength = 500)
        String assignmentDetail
) {}
