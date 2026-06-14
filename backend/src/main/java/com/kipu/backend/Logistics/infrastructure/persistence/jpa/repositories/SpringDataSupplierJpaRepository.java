package com.kipu.backend.Logistics.infrastructure.persistence.jpa.repositories;

import com.kipu.backend.Logistics.domain.model.valueobjects.Ruc;
import com.kipu.backend.Logistics.infrastructure.persistence.jpa.entities.SupplierJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataSupplierJpaRepository extends JpaRepository<SupplierJpaEntity, Long> {

    Optional<SupplierJpaEntity> findByRuc(Ruc ruc);

    List<SupplierJpaEntity> findByIsActive(Boolean isActive);

    boolean existsByRuc(Ruc ruc);
}