package com.kipu.backend.teamusers.domain.model.exceptions;

public class ExistingUserException extends RuntimeException {
    public ExistingUserException() {
        super("There's already a user with the selected full name");
    }
}
