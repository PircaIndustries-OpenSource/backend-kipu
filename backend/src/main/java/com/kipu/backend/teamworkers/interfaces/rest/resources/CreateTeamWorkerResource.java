package com.kipu.backend.teamworkers.interfaces.rest.resources;

import java.util.List;

public record CreateTeamWorkerResource(
        String dni,
        String fullName,
        String role,
        String projectId,
        List<TeamWorkerMachineryResource> machineries
) {}