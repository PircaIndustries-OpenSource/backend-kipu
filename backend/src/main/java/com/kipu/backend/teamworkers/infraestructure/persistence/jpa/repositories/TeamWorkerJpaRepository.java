package com.kipu.backend.teamworkers.infraestructure.persistence.jpa.repositories;

import com.kipu.backend.teamworkers.infraestructure.persistence.jpa.entities.TeamWorkerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamWorkerJpaRepository extends JpaRepository<TeamWorkerJpaEntity, String>,
        JpaSpecificationExecutor<TeamWorkerJpaEntity> {
}