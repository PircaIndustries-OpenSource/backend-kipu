package com.kipu.backend.Logistics.infrastructure.persistence.jpa.repositories;

import com.kipu.backend.Logistics.infrastructure.persistence.jpa.entities.MaterialCategoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataMaterialCategoryJpaRepository extends JpaRepository<MaterialCategoryJpaEntity, Long> {
}