package com.kipu.backend.teamusers.interfaces.rest.transform;

import com.kipu.backend.teamusers.domain.model.aggregates.TeamUser;
import com.kipu.backend.teamusers.interfaces.rest.resources.TeamUserResource;

public class TeamUserResourceFromEntityAssembler {
    public static TeamUserResource toResource(TeamUser teamUser) {
        return new TeamUserResource(
                teamUser.getId(),
                teamUser.getFullName().fullName(),
                teamUser.getEmail().address(),
                teamUser.getRole(),
                teamUser.isActive(),
                teamUser.getProjectId()
        );
    }
}
