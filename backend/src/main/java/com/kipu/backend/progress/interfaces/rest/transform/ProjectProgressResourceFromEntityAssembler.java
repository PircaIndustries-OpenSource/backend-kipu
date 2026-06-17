package com.kipu.backend.progress.interfaces.rest.transform;

import com.kipu.backend.progress.domain.model.aggregates.ProjectProgress;
import com.kipu.backend.progress.interfaces.rest.resources.ProjectProgressResource;

public class ProjectProgressResourceFromEntityAssembler {

    public static ProjectProgressResource toResourceFromEntity(ProjectProgress entity) {
        return new ProjectProgressResource(
                entity.getId(),
                entity.getProjectId(),
                entity.getProjectName(),
                entity.getActivityName(),
                entity.getDetails(),
                entity.getSpecialty(),
                entity.getStatus().name(),
                entity.getCurrentPercentage(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getLastUpdate(),
                entity.getResponsible(),
                entity.getWorkers(),
                entity.getWeather(),
                entity.isMiniAdvance(),
                entity.getParentId(),
                entity.getWeight()
        );
    }
}