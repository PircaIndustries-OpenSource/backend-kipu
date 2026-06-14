package com.kipu.backend.teamworkers.application.internal.commandservices;

import com.kipu.backend.teamworkers.application.commands.AssignMachineryToTeamWorkerCommand;
import com.kipu.backend.teamworkers.application.commands.CreateTeamWorkerCommand;
import com.kipu.backend.teamworkers.application.commands.DeleteTeamWorkerCommand;
import com.kipu.backend.teamworkers.application.commands.RemoveMachineryFromTeamWorkerCommand;
import com.kipu.backend.teamworkers.domain.model.aggregates.TeamWorker;

import java.util.Optional;

public interface TeamWorkerCommandService {
    Optional<TeamWorker> handle(CreateTeamWorkerCommand command);
    boolean handle(DeleteTeamWorkerCommand command);
    Optional<TeamWorker> handle(AssignMachineryToTeamWorkerCommand command);
    Optional<TeamWorker> handle(RemoveMachineryFromTeamWorkerCommand command);
}