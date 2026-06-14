package com.kipu.backend.iam.domain.model.exceptions;

import lombok.Getter;

/**
 * Domain exception thrown when registering a user with an email that is already in use.
 */
@Getter
public class EmailAlreadyExistsException extends RuntimeException {
    private final String email;

    public EmailAlreadyExistsException(String email) {
        super(String.format("User with email '%s' already exists.", email));
        this.email = email;
    }
}
