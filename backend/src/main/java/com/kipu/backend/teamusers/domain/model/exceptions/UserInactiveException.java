package com.kipu.backend.teamusers.domain.model.exceptions;

public class UserInactiveException extends RuntimeException {

    public UserInactiveException() {
        super("The user is already inactive");
    }
}
