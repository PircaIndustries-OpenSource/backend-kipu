package com.kipu.backend.teamworkers.interfaces.rest.transform;

import com.kipu.backend.teamworkers.domain.model.aggregates.TeamWorker;
import com.kipu.backend.teamworkers.interfaces.rest.resources.TeamWorkerMachineryResource;
import com.kipu.backend.teamworkers.interfaces.rest.resources.TeamWorkerResource;

import java.util.List;

public class TeamWorkerResourceFromEntityAssembler {

    public static TeamWorkerResource toResourceFromEntity(TeamWorker entity) {

        List<TeamWorkerMachineryResource> machineries = entity.getMachineries().stream()
                .map(m -> new TeamWorkerMachineryResource(m.getMachineryId(), m.getFullName()))
                .toList();

        return new TeamWorkerResource(
                entity.getId().value(),
                entity.getDni(),
                entity.getFullName(),
                entity.getRole(),
                entity.isActive(),
                entity.getProjectId(),
                machineries
        );
    }
}