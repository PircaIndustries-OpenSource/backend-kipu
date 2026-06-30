package com.kipu.backend.logistics.machinery.infrastructure.persistence.jpa.entities;

import com.kipu.backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "machinery")
@Getter
@Setter
public class MachineryJpaEntity {

    @Id
    @Column(nullable = false, unique = true, updatable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String status;

    private String assignedTo;
    private String registrationDate;
    private String maintenanceHours;
    private String assignmentDetail;
    
    @Column(nullable = false)
    private String projectId;

    public MachineryJpaEntity() {
        this.id = UUID.randomUUID().toString();
    }
}
