package com.kipu.backend.rnc.domain.model.valueobjects;

import com.kipu.backend.rnc.domain.model.valueobjects.external.UserId;

import java.util.Date;

/**
 * Value Object representing a solution log entry for an RNC.
 */
public record SolutionLog(
        Date date,
        String note,
        UserId author
) {
    public SolutionLog {
        if (note == null || note.isBlank()) {
            throw new IllegalArgumentException("Solution note cannot be empty.");
        }
        if (author == null) {
            throw new IllegalArgumentException("Solution author cannot be null.");
        }
        if (date == null) {
            date = new Date();
        }
    }
}