package com.kipu.backend.Logistics.machinery.interfaces.rest.transform;

import com.kipu.backend.Logistics.machinery.application.commands.UpdateMachineryCommand;
import com.kipu.backend.Logistics.machinery.domain.model.valueobjects.*;
import com.kipu.backend.Logistics.machinery.interfaces.rest.resources.UpdateMachineryResource;

public class UpdateMachineryCommandFromResourceAssembler {

    public static UpdateMachineryCommand toCommandFromResource(UpdateMachineryResource resource) {
        return new UpdateMachineryCommand(
                resource.name() != null ? new MachineryName(resource.name()) : null,
                resource.status() != null ? MachineryStatus.valueOf(resource.status()) : null,
                resource.assignedTo() != null ? new MachineryAssignedTo(resource.assignedTo()) : null,
                resource.assignedWorkerId(),
                resource.maintenanceHours() != null ? new MachineryMaintenanceHours(resource.maintenanceHours()) : null,
                resource.assignmentDetail() != null ? new MachineryAssignmentDetail(resource.assignmentDetail()) : null
        );
    }
}
