package com.kipu.backend.project.domain.model.exceptions;

/**
 * Exception thrown when a status change is attempted without providing the mandatory justification.
 */
public class JustificationRequiredException extends RuntimeException {
    public JustificationRequiredException() {
        super("Justification is required for status changes.");
    }
}
