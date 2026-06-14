package com.kipu.backend.teamworkers.application.queries;

public record GetAllTeamWorkersByProjectIdQuery(String projectId, String globalSearch) {}