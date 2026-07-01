package com.kipu.backend.Logistics.machinery.application.commands;

import com.kipu.backend.Logistics.machinery.domain.model.valueobjects.*;

public record UpdateMachineryCommand(
        MachineryName name,
        MachineryStatus status,
        MachineryAssignedTo assignedTo,
        String assignedWorkerId,
        MachineryMaintenanceHours maintenanceHours,
        MachineryAssignmentDetail assignmentDetail
) {
    // Null fields are allowed (PATCH semantics)
}
