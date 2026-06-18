package com.kipu.backend.teamusers.domain.model.exceptions;

public class ExistingUserException extends RuntimeException {
    public ExistingUserException() {
        super("user.validation.existingUser");
    }
}
