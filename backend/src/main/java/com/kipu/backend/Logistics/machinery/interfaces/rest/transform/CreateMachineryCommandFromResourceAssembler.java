package com.kipu.backend.Logistics.machinery.interfaces.rest.transform;

import com.kipu.backend.Logistics.machinery.application.commands.CreateMachineryCommand;
import com.kipu.backend.Logistics.machinery.domain.model.valueobjects.*;
import com.kipu.backend.Logistics.machinery.interfaces.rest.resources.CreateMachineryResource;

public class CreateMachineryCommandFromResourceAssembler {

    public static CreateMachineryCommand toCommandFromResource(CreateMachineryResource resource, String projectId) {
        return new CreateMachineryCommand(
                new MachineryName(resource.name()),
                resource.assignedTo() != null ? new MachineryAssignedTo(resource.assignedTo()) : new MachineryAssignedTo(null),
                new MachineryAssignmentDetail(resource.assignmentDetail()),
                projectId
        );
    }
}
