package com.kipu.backend.project.application.transform;

import com.kipu.backend.project.domain.model.entities.ProjectItem;

/**
 * DTO representing a project work item (Partida) returned to the client.
 */
public record ProjectItemResource(
        Long id,
        String name,
        String startDate,
        String endDate,
        String projectId
) {
    /**
     * Map ProjectItem domain entity to ProjectItemResource DTO.
     */
    public static ProjectItemResource fromProjectItem(ProjectItem item) {
        return new ProjectItemResource(
                item.getId(),
                item.getName(),
                item.getStartDate().toString(),
                item.getEndDate().toString(),
                item.getProject().getId()
        );
    }
}
