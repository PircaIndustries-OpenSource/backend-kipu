package com.kipu.backend.teamworkers.infraestructure.persistence.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "team_workerXmachinery") //
@Getter
@Setter
public class WorkerMachineryJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_machinery")
    private String machineryId;

    @Column(name = "id_worker")
    private String workerId;

    @Column(name = "fullName")
    private String fullName;
}