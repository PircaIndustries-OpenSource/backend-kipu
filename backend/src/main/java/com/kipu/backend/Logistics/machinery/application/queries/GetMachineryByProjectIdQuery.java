package com.kipu.backend.Logistics.machinery.application.queries;

public record GetMachineryByProjectIdQuery(String projectId) {
    public GetMachineryByProjectIdQuery {
        if (projectId == null || projectId.isBlank()) {
            throw new IllegalArgumentException("machinery.error.projectId.notBlank");
        }
    }
}
