package com.kipu.backend.iam.domain.model.exceptions;

/**
 * Domain exception thrown when a requested user cannot be found by their identifier.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super(String.format("User with ID %d was not found.", id));
    }

    public UserNotFoundException(String identifier) {
        super(String.format("User identified by '%s' was not found.", identifier));
    }
}
