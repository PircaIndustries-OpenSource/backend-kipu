package com.kipu.backend.Logistics.machinery.domain.model.aggregates;

import com.kipu.backend.Logistics.machinery.domain.model.valueobjects.*;

import java.time.Instant;
import java.util.Objects;

public class Machinery {

    private final String id;
    private final MachineryName name;
    private final MachineryStatus status;
    private final MachineryAssignedTo assignedTo;
    private final String assignedWorkerId;
    private final Instant registrationDate;
    private final MachineryMaintenanceHours maintenanceHours;
    private final MachineryAssignmentDetail assignmentDetail;
    private final String projectId;
    private final Instant createdAt;
    private final Instant updatedAt;

    private Machinery(String id, MachineryName name, MachineryStatus status, MachineryAssignedTo assignedTo,
                      String assignedWorkerId, Instant registrationDate, MachineryMaintenanceHours maintenanceHours,
                      MachineryAssignmentDetail assignmentDetail, String projectId,
                      Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "machinery.error.name.notBlank");
        this.status = Objects.requireNonNull(status, "machinery.error.status.notBlank");
        this.assignedTo = assignedTo;
        this.assignedWorkerId = assignedWorkerId;
        this.registrationDate = Objects.requireNonNull(registrationDate, "machinery.error.registrationDate.notBlank");
        this.maintenanceHours = Objects.requireNonNull(maintenanceHours, "machinery.error.maintenanceHours.notBlank");
        this.assignmentDetail = Objects.requireNonNull(assignmentDetail, "machinery.error.assignmentDetail.notBlank");
        this.projectId = projectId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Machinery create(MachineryName name, MachineryAssignedTo assignedTo,
                                   MachineryAssignmentDetail assignmentDetail, String projectId) {
        Instant now = Instant.now();
        return new Machinery(
                null, name, MachineryStatus.AVAILABLE, assignedTo, null,
                now, new MachineryMaintenanceHours("0"),
                assignmentDetail, projectId, now, now
        );
    }

    public static Machinery rehydrate(String id, MachineryName name, MachineryStatus status, MachineryAssignedTo assignedTo,
                                      String assignedWorkerId, Instant registrationDate, MachineryMaintenanceHours maintenanceHours,
                                      MachineryAssignmentDetail assignmentDetail, String projectId,
                                      Instant createdAt, Instant updatedAt) {
        return new Machinery(id, name, status, assignedTo, assignedWorkerId, registrationDate, maintenanceHours,
                assignmentDetail, projectId, createdAt, updatedAt);
    }

    public Machinery update(MachineryName name, MachineryStatus status, MachineryAssignedTo assignedTo,
                            String assignedWorkerId, MachineryMaintenanceHours maintenanceHours,
                            MachineryAssignmentDetail assignmentDetail) {
        return new Machinery(
                this.id,
                name != null ? name : this.name,
                status != null ? status : this.status,
                assignedTo != null ? assignedTo : this.assignedTo,
                assignedWorkerId != null ? assignedWorkerId : this.assignedWorkerId,
                this.registrationDate,
                maintenanceHours != null ? maintenanceHours : this.maintenanceHours,
                assignmentDetail != null ? assignmentDetail : this.assignmentDetail,
                this.projectId,
                this.createdAt,
                Instant.now()
        );
    }

    public String getId() { return id; }
    public MachineryName getName() { return name; }
    public MachineryStatus getStatus() { return status; }
    public MachineryAssignedTo getAssignedTo() { return assignedTo; }
    public String getAssignedWorkerId() { return assignedWorkerId; }
    public Instant getRegistrationDate() { return registrationDate; }
    public MachineryMaintenanceHours getMaintenanceHours() { return maintenanceHours; }
    public MachineryAssignmentDetail getAssignmentDetail() { return assignmentDetail; }
    public String getProjectId() { return projectId; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
