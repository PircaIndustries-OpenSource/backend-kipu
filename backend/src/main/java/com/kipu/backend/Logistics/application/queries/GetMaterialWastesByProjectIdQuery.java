package com.kipu.backend.Logistics.application.queries;

import com.kipu.backend.Logistics.domain.model.valueobjects.external.ProjectId;

/**
 * Query to retrieve all material waste records for a given project.
 *
 * @param projectId the project identifier value object
 */
public record GetMaterialWastesByProjectIdQuery(ProjectId projectId) {
    public GetMaterialWastesByProjectIdQuery {
        if (projectId == null) throw new IllegalArgumentException("projectId cannot be null");
    }
}
