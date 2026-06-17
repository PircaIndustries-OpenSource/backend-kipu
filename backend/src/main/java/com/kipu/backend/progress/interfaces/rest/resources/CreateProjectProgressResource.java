package com.kipu.backend.progress.interfaces.rest.resources;

public record CreateProjectProgressResource(
        String projectId,
        String projectName,
        String activityName,
        String details,
        String specialty,
        Integer percentage,
        String responsible,
        Integer workers,
        String weather,
        Integer weight,
        boolean isMiniAdvance,
        Long parentId
) {}