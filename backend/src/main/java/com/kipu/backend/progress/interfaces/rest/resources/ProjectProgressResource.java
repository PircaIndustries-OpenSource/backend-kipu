package com.kipu.backend.progress.interfaces.rest.resources;

import java.util.Date;

public record ProjectProgressResource(
        Long id,
        String projectId,
        String projectName,
        String activityName,
        String details,
        String specialty,
        String status,
        Integer currentPercentage,
        Date startDate,
        Date endDate,
        Date lastUpdate,
        String responsible,
        Integer workers,
        String weather,
        boolean isMiniAdvance,
        Long parentId,
        Integer weight
) {}