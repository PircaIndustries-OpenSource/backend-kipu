package com.kipu.backend.Logistics.machinery.infrastructure.persistence.jpa.repositories;

import com.kipu.backend.Logistics.machinery.infrastructure.persistence.jpa.entities.MachineryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataMachineryJpaRepository extends JpaRepository<MachineryJpaEntity, String> {
    List<MachineryJpaEntity> findByProjectId(String projectId);
}
