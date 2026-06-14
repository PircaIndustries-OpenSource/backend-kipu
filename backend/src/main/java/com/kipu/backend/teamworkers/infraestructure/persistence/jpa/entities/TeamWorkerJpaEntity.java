package com.kipu.backend.teamworkers.infraestructure.persistence.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "worker") // Exactamente como en tu ERD
@Getter
@Setter
public class TeamWorkerJpaEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "dni")
    private String dni;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "role")
    private String role;

    @Column(name = "isActive")
    private boolean isActive;

    @Column(name = "project_id")
    private String projectId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_worker")
    private List<WorkerMachineryJpaEntity> machineries = new ArrayList<>();
}