package com.kipu.backend.Logistics.machinery.infrastructure.persistence.jpa.entities;

import com.kipu.backend.Logistics.machinery.domain.model.valueobjects.*;
import com.kipu.backend.Logistics.machinery.infrastructure.persistence.jpa.converters.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "machinery")
public class MachineryJpaEntity {

    @Id
    @Column(nullable = false, unique = true, updatable = false, length = 36)
    private String id;

    @Column(name = "name", nullable = false, length = 200)
    @Convert(converter = MachineryNameAttributeConverter.class)
    private MachineryName name;

    @Column(name = "status", nullable = false, length = 30)
    @Convert(converter = MachineryStatusAttributeConverter.class)
    private MachineryStatus status;

    @Column(name = "assigned_to", length = 100)
    @Convert(converter = MachineryAssignedToAttributeConverter.class)
    private MachineryAssignedTo assignedTo;

    @Column(name = "registration_date", nullable = false)
    private Instant registrationDate;

    @Column(name = "maintenance_hours", nullable = false, length = 10)
    @Convert(converter = MachineryMaintenanceHoursAttributeConverter.class)
    private MachineryMaintenanceHours maintenanceHours;

    @Column(name = "assignment_detail", nullable = false, length = 500)
    @Convert(converter = MachineryAssignmentDetailAttributeConverter.class)
    private MachineryAssignmentDetail assignmentDetail;

    @Column(name = "project_id", nullable = false, length = 36)
    private String projectId;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Instant updatedAt;

    public MachineryJpaEntity(String id, MachineryName name, MachineryStatus status, MachineryAssignedTo assignedTo,
                              Instant registrationDate, MachineryMaintenanceHours maintenanceHours,
                              MachineryAssignmentDetail assignmentDetail, String projectId,
                              Instant createdAt, Instant updatedAt) {
        this.id = id != null ? id : UUID.randomUUID().toString();
        this.name = name;
        this.status = status;
        this.assignedTo = assignedTo;
        this.registrationDate = registrationDate;
        this.maintenanceHours = maintenanceHours;
        this.assignmentDetail = assignmentDetail;
        this.projectId = projectId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
