package com.kipu.backend.teamusers.infraestructure.persistence.jpa.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "TeamUser")
@Table(name = "team_user")
@Getter
@Setter
public class TeamUserJpaEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private String role;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "project_id")
    private String projectId;

    public TeamUserJpaEntity() {}

    public TeamUserJpaEntity(String fullName, String email, String role, String projectId) {
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.isActive = true;
        this.projectId = projectId;
    }
}
