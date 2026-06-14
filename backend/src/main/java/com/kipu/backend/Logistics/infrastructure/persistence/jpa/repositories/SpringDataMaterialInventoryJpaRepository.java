package com.kipu.backend.Logistics.infrastructure.persistence.jpa.repositories;

import com.kipu.backend.Logistics.domain.model.valueobjects.MaterialCatalogId;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.entities.MaterialInventoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataMaterialInventoryJpaRepository extends JpaRepository<MaterialInventoryJpaEntity, Long> {

    @Query("SELECT mi FROM MaterialInventoryJpaEntity mi WHERE mi.materialCatalogId IN :catalogIds")
    List<MaterialInventoryJpaEntity> findByMaterialCatalogIdIn(@Param("catalogIds") List<MaterialCatalogId> catalogIds);
}