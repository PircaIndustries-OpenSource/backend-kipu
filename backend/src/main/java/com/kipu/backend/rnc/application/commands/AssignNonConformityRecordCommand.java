package com.kipu.backend.rnc.application.commands;

/**
 * Command to assign an existing RNC to a user for resolution.
 */
public record AssignNonConformityRecordCommand(
        String rncId,
        String assigneeId
) {
}