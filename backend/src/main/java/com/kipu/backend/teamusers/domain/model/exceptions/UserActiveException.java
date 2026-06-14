package com.kipu.backend.teamusers.domain.model.exceptions;

public class UserActiveException extends RuntimeException {

    public UserActiveException() {

        super("The user is already active");
    }
}
