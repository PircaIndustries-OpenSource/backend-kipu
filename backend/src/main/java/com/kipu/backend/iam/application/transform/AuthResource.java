package com.kipu.backend.iam.application.transform;

/**
 * Data Transfer Object representing authentication success containing JWT and identity details.
 */
public record AuthResource(
        String token,
        Long id,
        String email,
        String role
) {}
