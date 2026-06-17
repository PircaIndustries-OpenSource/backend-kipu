package com.kipu.backend.progress.infrastructure.persistence.jpa.entities;

import com.kipu.backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "project_progresses")
@Getter
@Setter
public class ProjectProgressJpaEntity extends AuditableAbstractPersistenceEntity {


    @Column(nullable = false)
    private String projectId;

    private String projectName;
    private String activityName;
    private String details;
    private String specialty;

    @Column(nullable = false)
    private String status;

    private Integer currentPercentage;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;

    private String responsible;
    private Integer workers;
    private String weather;

    private boolean isMiniAdvance;
    private Long parentId;
    private Integer weight;
}