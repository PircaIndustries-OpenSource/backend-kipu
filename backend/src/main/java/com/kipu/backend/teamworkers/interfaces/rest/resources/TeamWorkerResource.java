package com.kipu.backend.teamworkers.interfaces.rest.resources;

import java.util.List;

public record TeamWorkerResource(
        String id,
        String dni,
        String fullName,
        String role,
        boolean isActive,
        String projectId,
        List<TeamWorkerMachineryResource> machineries
) {}