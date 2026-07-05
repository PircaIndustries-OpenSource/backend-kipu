package com.kipu.backend.rnc.application.commands;

/**
 * Command to add a solution log note to an existing RNC.
 */
public record AddSolutionNoteCommand(
        String rncId,
        String note,
        String authorId
) {
}