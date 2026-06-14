package com.kipu.backend.iam.application.transform;

import com.kipu.backend.iam.domain.model.aggregates.User;

/**
 * Data Transfer Object representing user identity, aligning with Angular frontend.
 */
public record IdentityResource(
        String id,
        String email,
        String name,
        String role
) {
    /**
     * Map User domain aggregate to IdentityResource DTO.
     */
    public static IdentityResource fromUser(User user) {
        return new IdentityResource(
                String.valueOf(user.getId()),
                user.getEmail(),
                user.getUsername(), // frontend 'name' maps to backend 'username'
                user.getRole().name()
        );
    }
}
