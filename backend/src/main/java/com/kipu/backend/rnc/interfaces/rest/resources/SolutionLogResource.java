package com.kipu.backend.rnc.interfaces.rest.resources;

import java.util.Date;

/**
 * Resource DTO representing a solution log entry.
 */
public record SolutionLogResource(
        Date date,
        String note,
        String authorId
) {
}