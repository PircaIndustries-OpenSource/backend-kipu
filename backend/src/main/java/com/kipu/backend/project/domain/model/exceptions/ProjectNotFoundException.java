package com.kipu.backend.project.domain.model.exceptions;

import lombok.Getter;

/**
 * Exception thrown when a project cannot be found by its identifier.
 */
@Getter
public class ProjectNotFoundException extends RuntimeException {
    private final String id;

    public ProjectNotFoundException(String id) {
        super(String.format("Project with ID '%s' was not found.", id));
        this.id = id;
    }
}
