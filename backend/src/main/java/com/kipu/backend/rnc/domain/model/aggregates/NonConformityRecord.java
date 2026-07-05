package com.kipu.backend.rnc.domain.model.aggregates;

import com.kipu.backend.rnc.domain.model.valueobjects.RncStatus;
import com.kipu.backend.rnc.domain.model.valueobjects.Severity;
import com.kipu.backend.rnc.domain.model.valueobjects.SolutionLog;
import com.kipu.backend.rnc.domain.model.valueobjects.Specialty;
import com.kipu.backend.rnc.domain.model.valueobjects.external.ProjectId;
import com.kipu.backend.rnc.domain.model.valueobjects.external.UserId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Aggregate Root for the Non-Conformance Record (RNC).
 * It encapsulates the state and enforces business rules for an RNC.
 */
public class NonConformityRecord {

    private String id;
    private ProjectId projectId;
    private String title;
    private String description;
    private Specialty specialty;
    private String location;
    private Severity severity;
    private RncStatus status;
    private UserId reportedBy;
    private Date reportDate;
    private List<String> images;
    private UserId assignedTo;
    private Date resolutionDate;
    private List<SolutionLog> solutionNotes;

    /**
     * Private constructor for rehydration purposes (e.g., from Persistence Mappers).
     */
    private NonConformityRecord() {
        this.images = new ArrayList<>();
        this.solutionNotes = new ArrayList<>();
    }

    /**
     * Business constructor to create a new NonConformityRecord.
     * Enforces invariants on creation.
     */
    public NonConformityRecord(ProjectId projectId, String title, String description, Specialty specialty,
                               String location, Severity severity, UserId reportedBy, List<String> images) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Title cannot be empty.");
        if (description == null || description.isBlank()) throw new IllegalArgumentException("Description cannot be empty.");
        if (location == null || location.isBlank()) throw new IllegalArgumentException("Location cannot be empty.");

        this.id = UUID.randomUUID().toString();
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.specialty = specialty;
        this.location = location;
        this.severity = severity;
        this.status = RncStatus.CREATED; // Default initial status
        this.reportedBy = reportedBy;
        this.reportDate = new Date();
        this.images = images != null ? new ArrayList<>(images) : new ArrayList<>();
        this.solutionNotes = new ArrayList<>();
    }

    // --- Business Behaviors ---

    public void assignTo(UserId assignee) {
        if (this.status == RncStatus.VERIFIED || this.status == RncStatus.SOLVED) {
            throw new IllegalArgumentException("Cannot assign an RNC that is already solved or verified.");
        }
        this.assignedTo = assignee;
        this.status = RncStatus.ASSIGNED;
    }

    public void addSolutionNote(String note, UserId author) {
        SolutionLog log = new SolutionLog(new Date(), note, author);
        this.solutionNotes.add(log);
    }

    public void markAsSolved() {
        if (this.status != RncStatus.ASSIGNED) {
            throw new IllegalArgumentException("RNC must be assigned before it can be solved.");
        }
        this.status = RncStatus.SOLVED;
        this.resolutionDate = new Date();
    }

    public void verifySolution() {
        if (this.status != RncStatus.SOLVED) {
            throw new IllegalArgumentException("RNC must be solved before it can be verified.");
        }
        this.status = RncStatus.VERIFIED;
    }

    // --- Getters ---

    public String getId() { return id; }
    public ProjectId getProjectId() { return projectId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Specialty getSpecialty() { return specialty; }
    public String getLocation() { return location; }
    public Severity getSeverity() { return severity; }
    public RncStatus getStatus() { return status; }
    public UserId getReportedBy() { return reportedBy; }
    public Date getReportDate() { return reportDate; }
    public List<String> getImages() { return List.copyOf(images); }
    public UserId getAssignedTo() { return assignedTo; }
    public Date getResolutionDate() { return resolutionDate; }
    public List<SolutionLog> getSolutionNotes() { return List.copyOf(solutionNotes); }
}