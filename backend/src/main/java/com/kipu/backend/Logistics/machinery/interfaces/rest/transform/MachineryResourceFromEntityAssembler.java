package com.kipu.backend.Logistics.machinery.interfaces.rest.transform;

import com.kipu.backend.Logistics.machinery.domain.model.aggregates.Machinery;
import com.kipu.backend.Logistics.machinery.interfaces.rest.resources.MachineryResource;

public class MachineryResourceFromEntityAssembler {

    public static MachineryResource toResourceFromEntity(Machinery entity) {
        return new MachineryResource(
                entity.getId(),
                entity.getName().value(),
                entity.getStatus().name(),
                entity.getAssignedTo() != null ? entity.getAssignedTo().value() : null,
                entity.getRegistrationDate() != null ? entity.getRegistrationDate().toString() : null,
                entity.getMaintenanceHours() != null ? entity.getMaintenanceHours().value() : null,
                entity.getAssignmentDetail() != null ? entity.getAssignmentDetail().value() : null
        );
    }
}
