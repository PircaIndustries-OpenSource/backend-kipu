package com.kipu.backend.project.domain.model.aggregates;

import com.kipu.backend.project.domain.model.exceptions.JustificationRequiredException;
import com.kipu.backend.project.domain.model.valueobjects.ProjectStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Project aggregate root representing a construction project in Kipu.
 */
@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectStatus status;

    @Column(nullable = false)
    private String startDate;

    private String endDate;

    @Column(nullable = false)
    private Double totalBudget;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String createdAt;

    @Column(nullable = false)
    private String createdBy;

    @Column(length = 500)
    private String statusJustification;

    private String imageUrl;

    /**
     * DDD constructor for creating a new Project.
     */
    public Project(String name, String description, String startDate, String endDate,
                   Double totalBudget, String location, String createdBy, String imageUrl) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.status = ProjectStatus.PLANNED;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalBudget = totalBudget;
        this.location = location;
        this.createdAt = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        this.createdBy = createdBy;
        this.imageUrl = imageUrl != null ? imageUrl : "project-default.png";
    }

    /**
     * DDD business method to update the project status with a required justification.
     */
    public void updateStatus(ProjectStatus status, String justification) {
        if (status == null) {
            throw new IllegalArgumentException("project.error.invalidStatus");
        }
        if (justification == null || justification.trim().isEmpty()) {
            throw new JustificationRequiredException();
        }
        this.status = status;
        this.statusJustification = justification;
    }
}
