package com.kipu.backend.teamusers.application.queries;

public record GetTeamUsersByEmailQuery(String email) {
    public GetTeamUsersByEmailQuery {
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("user.validation.emailRequired");
    }
}
