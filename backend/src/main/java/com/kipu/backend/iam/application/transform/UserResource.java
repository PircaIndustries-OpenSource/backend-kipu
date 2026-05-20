package com.kipu.backend.iam.application.transform;

import com.kipu.backend.iam.domain.model.aggregates.User;

/**
 * Data Transfer Object representing user details, excluding security credentials.
 */
public record UserResource(
        Long id,
        String username,
        String email,
        String role
) {
    /**
     * Static utility mapper to transform User aggregate to UserResource.
     */
    public static UserResource fromUser(User user) {
        return new UserResource(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}
