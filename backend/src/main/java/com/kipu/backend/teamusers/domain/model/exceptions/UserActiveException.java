package com.kipu.backend.teamusers.domain.model.exceptions;

public class UserActiveException extends RuntimeException {

    public UserActiveException() {

        super("user.validation.alreadyActive");
    }
}
