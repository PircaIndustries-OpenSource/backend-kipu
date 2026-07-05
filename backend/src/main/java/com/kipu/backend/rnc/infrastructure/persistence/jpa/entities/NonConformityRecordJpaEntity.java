package com.kipu.backend.rnc.infrastructure.persistence.jpa.entities;

import com.kipu.backend.rnc.domain.model.valueobjects.RncStatus;
import com.kipu.backend.rnc.domain.model.valueobjects.Severity;
import com.kipu.backend.rnc.domain.model.valueobjects.Specialty;
import com.kipu.backend.rnc.infrastructure.persistence.jpa.embeddables.SolutionLogEmbeddable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "non_conformity_records")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class NonConformityRecordJpaEntity {

    @Id
    private String id;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Date updatedAt;

    @Column(nullable = false)
    private String projectId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Specialty specialty;

    @Column(nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Severity severity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RncStatus status;

    @Column(nullable = false)
    private String reportedBy;

    @Column(nullable = false)
    private Date reportDate;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> images;

    private String assignedTo;

    private Date resolutionDate;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<SolutionLogEmbeddable> solutionNotes;
}