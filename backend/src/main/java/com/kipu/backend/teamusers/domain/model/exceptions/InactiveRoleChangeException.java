package com.kipu.backend.teamusers.domain.model.exceptions;

public class InactiveRoleChangeException extends RuntimeException {

    public InactiveRoleChangeException() {
        super("user.validation.inactiveRoleChange");
    }
}
