package com.kipu.backend.Logistics.infrastructure.persistence.jpa.repositories;

import com.kipu.backend.Logistics.domain.model.valueobjects.CategoryId;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.entities.MaterialCatalogJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataMaterialCatalogJpaRepository extends JpaRepository<MaterialCatalogJpaEntity, Long> {

    List<MaterialCatalogJpaEntity> findByCategoryId(CategoryId categoryId);
}