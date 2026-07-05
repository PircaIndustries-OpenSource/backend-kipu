package com.kipu.backend.rnc.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

/**
 * Resource DTO for adding a solution note.
 */
public record AddSolutionNoteResource(
        @NotBlank(message = "Note cannot be blank") String note,
        @NotBlank(message = "Author ID cannot be blank") String authorId
) {
}