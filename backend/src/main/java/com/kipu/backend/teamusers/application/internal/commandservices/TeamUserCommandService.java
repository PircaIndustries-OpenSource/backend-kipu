package com.kipu.backend.teamusers.application.internal.commandservices;

import com.kipu.backend.teamusers.application.commands.ActivateTeamUserCommand;
import com.kipu.backend.teamusers.application.commands.CreateTeamUserCommand;
import com.kipu.backend.teamusers.application.commands.DeactiveTeamUserCommand;
import com.kipu.backend.teamusers.domain.model.aggregates.TeamUser;

import java.util.Optional;

public interface TeamUserCommandService {
    Optional<TeamUser> handle(CreateTeamUserCommand command);
    Optional<TeamUser> handle(DeactiveTeamUserCommand command);
    Optional<TeamUser> handle(ActivateTeamUserCommand command);
}
