package com.kipu.backend.teamusers.domain.model.exceptions;

public class InactiveRoleChangeException extends RuntimeException {

    public InactiveRoleChangeException() {
        super("Inactive users cannot change their role");
    }
}
