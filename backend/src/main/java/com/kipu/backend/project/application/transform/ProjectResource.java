package com.kipu.backend.project.application.transform;

import com.kipu.backend.project.domain.model.aggregates.Project;

/**
 * DTO representing a project returned to the HTTP client.
 * Aligns perfectly with the Angular frontend's ProjectResource.
 */
public record ProjectResource(
        String id,
        String name,
        String description,
        String status,
        String startDate,
        String endDate,
        Double totalBudget,
        String location,
        String createdAt,
        String createdBy,
        String statusJustification,
        String imageUrl
) {
    /**
     * Map Project domain aggregate to ProjectResource DTO.
     */
    public static ProjectResource fromProject(Project project) {
        return new ProjectResource(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getStatus().name(),
                project.getStartDate(),
                project.getEndDate(),
                project.getTotalBudget(),
                project.getLocation(),
                project.getCreatedAt(),
                project.getCreatedBy(),
                project.getStatusJustification(),
                project.getImageUrl()
        );
    }
}
