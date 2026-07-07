package com.kipu.backend.Logistics.application.queries;

public record GetMaterialRequestsByProjectQuery(String projectId) {
    public GetMaterialRequestsByProjectQuery {
        if (projectId == null || projectId.isBlank())
            throw new IllegalArgumentException("projectId cannot be null or blank");
    }
}
