package com.kipu.backend.progress.domain.model.aggregates;

import com.kipu.backend.progress.domain.model.valueobjects.ProgressStatus;
import com.kipu.backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Progress aggregate root handling construction activity advancements.
 * Supports hierarchical parent-child log relationships for sub-advances mapping.
 */
@Getter
@Setter
public class ProjectProgress extends AbstractDomainAggregateRoot<ProjectProgress> {

    private Long id;
    private String projectId;
    private String projectName;
    private String activityName;
    private String details;
    private String specialty;
    private ProgressStatus status;
    private Integer currentPercentage;
    private Date startDate;
    private Date endDate;
    private Date lastUpdate;
    private String responsible;
    private Integer workers;
    private String weather;
    private boolean isMiniAdvance;
    private Long parentId;
    private Integer weight;

    public ProjectProgress() {
        this.status = ProgressStatus.ACTIVE;
        this.currentPercentage = 0;
        this.isMiniAdvance = false;
        this.weight = 1;
    }

    public ProjectProgress(String projectId, String projectName, String activityName,
                           String details, String specialty, String responsible,
                           Integer workers, String weather, Integer weight) {
        this();
        this.projectId = projectId;
        this.projectName = projectName;
        this.activityName = activityName;
        this.details = details;
        this.specialty = specialty;
        this.startDate = new Date();
        this.endDate = new Date();
        this.lastUpdate = new Date();
        this.responsible = responsible;
        this.workers = workers;
        this.weather = weather;
        this.isMiniAdvance = false;
        this.parentId = null;
        this.weight = weight != null ? weight : 1;
    }

    public void updateCompletionProgress(Integer totalPercentage) {
        if (totalPercentage < 0 || totalPercentage > 100) {
            throw new IllegalArgumentException("progress.error.percentage.outOfBounds");
        }
        this.currentPercentage = totalPercentage;
        this.status = totalPercentage == 100 ? ProgressStatus.FINISHED : ProgressStatus.ACTIVE;
        this.lastUpdate = new Date();
    }
}