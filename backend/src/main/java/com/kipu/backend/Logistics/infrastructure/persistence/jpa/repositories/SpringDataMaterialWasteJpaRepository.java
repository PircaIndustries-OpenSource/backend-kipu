package com.kipu.backend.Logistics.infrastructure.persistence.jpa.repositories;

import com.kipu.backend.Logistics.infrastructure.persistence.jpa.entities.MaterialWasteJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataMaterialWasteJpaRepository extends JpaRepository<MaterialWasteJpaEntity, Long> {

    List<MaterialWasteJpaEntity> findByProjectId(String projectId);
}
