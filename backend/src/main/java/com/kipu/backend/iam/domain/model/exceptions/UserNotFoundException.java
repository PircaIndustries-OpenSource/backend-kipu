package com.kipu.backend.iam.domain.model.exceptions;

import lombok.Getter;

/**
 * Domain exception thrown when a requested user cannot be found by their identifier.
 */
@Getter
public class UserNotFoundException extends RuntimeException {
    private final String identifier;

    public UserNotFoundException(Long id) {
        super(String.format("User with ID %d was not found.", id));
        this.identifier = String.valueOf(id);
    }

    public UserNotFoundException(String identifier) {
        super(String.format("User identified by '%s' was not found.", identifier));
        this.identifier = identifier;
    }
}
