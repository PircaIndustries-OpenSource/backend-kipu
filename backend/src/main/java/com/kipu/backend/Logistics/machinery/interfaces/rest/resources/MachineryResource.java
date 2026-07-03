package com.kipu.backend.Logistics.machinery.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response resource representing a machinery entry")
public record MachineryResource(
        @Schema(description = "Persistent identifier", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
        String id,

        @Schema(description = "Machinery name/description", example = "Excavadora CAT 320")
        String name,

        @Schema(description = "Current status", example = "AVAILABLE")
        String status,

        @Schema(description = "Worker assigned (DNI - FullName)", example = "12345678 - Juan Pérez")
        String assignedTo,

        @Schema(description = "Registration date", example = "2024-01-15T10:30:00Z")
        String registrationDate,

        @Schema(description = "Maintenance hours accumulated", example = "120")
        String maintenanceHours,

        @Schema(description = "Assignment detail/purpose", example = "Asignado para excavación en zona A")
        String assignmentDetail
) {}
