package com.kipu.backend.Logistics.infrastructure.persistence.jpa.repositories;

import com.kipu.backend.Logistics.domain.model.valueobjects.MaterialCatalogId;
import com.kipu.backend.Logistics.domain.model.valueobjects.SupplierId;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.entities.SupplierOfferJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataSupplierOfferJpaRepository extends JpaRepository<SupplierOfferJpaEntity, Long> {
    List<SupplierOfferJpaEntity> findBySupplierId(SupplierId supplierId);
    List<SupplierOfferJpaEntity> findByMaterialCatalogId(MaterialCatalogId materialCatalogId);
    Optional<SupplierOfferJpaEntity> findBySupplierIdAndMaterialCatalogId(SupplierId supplierId, MaterialCatalogId materialCatalogId);
}
