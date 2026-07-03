package com.kipu.backend.teamusers.application.commands;

import com.kipu.backend.teamusers.domain.model.valueobjects.EmailAddress;
import com.kipu.backend.teamusers.domain.model.valueobjects.FullName;

public record CreateTeamUserCommand(
        Long userId,
        FullName fullName,
        EmailAddress emailAddress,
        String role,
        String projectId
) {
}
