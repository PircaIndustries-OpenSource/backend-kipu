package com.kipu.backend.team.infrastructure.persistence.jpa.entities;

import com.kipu.backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "invitations")
@Getter
@Setter
public class InvitationJpaEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String status;

    private String projectId;
}
