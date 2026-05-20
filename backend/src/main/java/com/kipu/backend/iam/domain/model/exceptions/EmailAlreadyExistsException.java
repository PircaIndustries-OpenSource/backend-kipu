package com.kipu.backend.iam.domain.model.exceptions;

/**
 * Domain exception thrown when registering a user with an email that is already in use.
 */
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super(String.format("User with email '%s' already exists.", email));
    }
}
