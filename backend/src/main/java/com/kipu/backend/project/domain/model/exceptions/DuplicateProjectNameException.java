package com.kipu.backend.project.domain.model.exceptions;

import lombok.Getter;

/**
 * Exception thrown when trying to create a project with a name that already exists in the system.
 */
@Getter
public class DuplicateProjectNameException extends RuntimeException {
    private final String name;

    public DuplicateProjectNameException(String name) {
        super(String.format("Project with name '%s' already exists.", name));
        this.name = name;
    }
}
