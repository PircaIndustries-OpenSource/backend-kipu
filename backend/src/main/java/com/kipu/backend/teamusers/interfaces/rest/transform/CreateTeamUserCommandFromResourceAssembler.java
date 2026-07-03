package com.kipu.backend.teamusers.interfaces.rest.transform;

import com.kipu.backend.teamusers.application.commands.CreateTeamUserCommand;
import com.kipu.backend.teamusers.domain.model.valueobjects.EmailAddress;
import com.kipu.backend.teamusers.domain.model.valueobjects.FullName;
import com.kipu.backend.teamusers.interfaces.rest.resources.CreateTeamUserResource;

public class CreateTeamUserCommandFromResourceAssembler {
    public static CreateTeamUserCommand toCommand(CreateTeamUserResource resource) {
        return new CreateTeamUserCommand(
                resource.userId(),
                new FullName(resource.fullName()),
                new EmailAddress(resource.email()),
                resource.role(),
                resource.projectId()
        );
    }
}
