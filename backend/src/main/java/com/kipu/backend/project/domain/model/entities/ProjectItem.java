package com.kipu.backend.project.domain.model.entities;

import com.kipu.backend.project.domain.model.aggregates.Project;
import com.kipu.backend.project.domain.model.exceptions.InvalidProjectItemDatesException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Entity representing a project work item (Partida) associated with a Project.
 */
@Entity
@Table(name = "project_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    /**
     * Constructor for creating a new ProjectItem with date validation rules.
     */
    public ProjectItem(String name, LocalDate startDate, LocalDate endDate, Project project) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Partida name cannot be blank");
        }
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Dates cannot be null");
        }
        if (endDate.isBefore(startDate)) {
            throw new InvalidProjectItemDatesException();
        }
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.project = project;
    }
}
