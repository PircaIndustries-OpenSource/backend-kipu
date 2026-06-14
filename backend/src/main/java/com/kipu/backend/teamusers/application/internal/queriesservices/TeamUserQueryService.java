package com.kipu.backend.teamusers.application.internal.queriesservices;

import com.kipu.backend.teamusers.application.queries.*;
import com.kipu.backend.teamusers.domain.model.aggregates.TeamUser;

import java.util.List;
import java.util.Optional;

public interface TeamUserQueryService {
    List<TeamUser> handle(GetAllTeamUsersQuery query);
    Optional<TeamUser> handle(GetUserByIdQuery query);
    List<TeamUser> handle(SearchTeamUsersQuery query);
    List<TeamUser> handle(GetAllActiveTeamUsersQuery query);
}
