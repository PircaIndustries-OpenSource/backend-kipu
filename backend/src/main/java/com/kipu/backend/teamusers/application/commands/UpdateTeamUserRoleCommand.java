package com.kipu.backend.teamusers.application.commands;

public record UpdateTeamUserRoleCommand(String id, String newRole) {
    public UpdateTeamUserRoleCommand {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("user.validation.idRequired");
        if (newRole == null || newRole.isBlank()) throw new IllegalArgumentException("user.validation.roleRequired");
    }
}
