package com.kipu.backend.teamworkers.interfaces.rest.transform;

import com.kipu.backend.teamworkers.application.commands.CreateTeamWorkerCommand;
import com.kipu.backend.teamworkers.application.commands.TeamWorkerMachineryItem;
import com.kipu.backend.teamworkers.interfaces.rest.resources.CreateTeamWorkerResource;

import java.util.Collections;
import java.util.List;

public class CreateTeamWorkerCommandFromResourceAssembler {

    public static CreateTeamWorkerCommand toCommandFromResource(CreateTeamWorkerResource resource) {

        List<TeamWorkerMachineryItem> machineries = resource.machineries() != null
                ? resource.machineries().stream()
                .map(m -> new TeamWorkerMachineryItem(m.machineryId(), m.fullName()))
                .toList()
                : Collections.emptyList();

        return new CreateTeamWorkerCommand(
                resource.dni(),
                resource.fullName(),
                resource.role(),
                resource.projectId(),
                machineries
        );
    }
}