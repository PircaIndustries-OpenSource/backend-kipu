package com.kipu.backend.teamusers.interfaces.rest.resources;

public record TeamUserResource(
        String id,
        String fullName,
        String email,
        String role,
        boolean isActive,
        String projectId) {
}
