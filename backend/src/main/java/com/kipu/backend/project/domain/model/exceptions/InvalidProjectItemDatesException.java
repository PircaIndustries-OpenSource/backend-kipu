package com.kipu.backend.project.domain.model.exceptions;

/**
 * Exception thrown when a project item (partida) has an end date before its start date.
 */
public class InvalidProjectItemDatesException extends RuntimeException {
    public InvalidProjectItemDatesException() {
        super("End date cannot be before start date.");
    }
}
