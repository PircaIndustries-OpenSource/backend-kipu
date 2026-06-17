package com.kipu.backend.progress.infrastructure.persistence.jpa.mappers;

import com.kipu.backend.progress.domain.model.aggregates.ProjectProgress;
import com.kipu.backend.progress.domain.model.valueobjects.ProgressStatus;
import com.kipu.backend.progress.infrastructure.persistence.jpa.entities.ProjectProgressJpaEntity;

public class ProjectProgressMapper {

    public static ProjectProgress toDomain(ProjectProgressJpaEntity entity) {
        if (entity == null) return null;
        ProjectProgress domain = new ProjectProgress();
        domain.setId(entity.getId());
        domain.setProjectId(entity.getProjectId());
        domain.setProjectName(entity.getProjectName());
        domain.setActivityName(entity.getActivityName());
        domain.setDetails(entity.getDetails());
        domain.setSpecialty(entity.getSpecialty());
        domain.setStatus(ProgressStatus.valueOf(entity.getStatus()));
        domain.setCurrentPercentage(entity.getCurrentPercentage());
        domain.setStartDate(entity.getStartDate());
        domain.setEndDate(entity.getEndDate());
        domain.setLastUpdate(entity.getLastUpdate());
        domain.setResponsible(entity.getResponsible());
        domain.setWorkers(entity.getWorkers());
        domain.setWeather(entity.getWeather());
        domain.setMiniAdvance(entity.isMiniAdvance());
        domain.setParentId(entity.getParentId());
        domain.setWeight(entity.getWeight());
        return domain;
    }

    public static ProjectProgressJpaEntity toJpaEntity(ProjectProgress domain) {
        if (domain == null) return null;
        ProjectProgressJpaEntity entity = new ProjectProgressJpaEntity();
        entity.setId(domain.getId());
        entity.setProjectId(domain.getProjectId());
        entity.setProjectName(domain.getProjectName());
        entity.setActivityName(domain.getActivityName());
        entity.setDetails(domain.getDetails());
        entity.setSpecialty(domain.getSpecialty());
        entity.setStatus(domain.getStatus().name());
        entity.setCurrentPercentage(domain.getCurrentPercentage());
        entity.setStartDate(domain.getStartDate());
        entity.setEndDate(domain.getEndDate());
        entity.setLastUpdate(domain.getLastUpdate());
        entity.setResponsible(domain.getResponsible());
        entity.setWorkers(domain.getWorkers());
        entity.setWeather(domain.getWeather());
        entity.setMiniAdvance(domain.isMiniAdvance());
        entity.setParentId(domain.getParentId());
        entity.setWeight(domain.getWeight());
        return entity;
    }
}