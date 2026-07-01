package com.kipu.backend.Logistics.machinery.application.commands;

import com.kipu.backend.Logistics.machinery.domain.model.valueobjects.*;

public record CreateMachineryCommand(
        MachineryName name,
        MachineryAssignedTo assignedTo,
        MachineryAssignmentDetail assignmentDetail,
        String projectId
) {
    public CreateMachineryCommand {
        if (name == null)
            throw new IllegalArgumentException("machinery.error.name.notBlank");
        if (assignmentDetail == null)
            throw new IllegalArgumentException("machinery.error.assignmentDetail.notBlank");
    }
}
