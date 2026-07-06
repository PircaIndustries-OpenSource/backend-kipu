package com.kipu.backend.Logistics.machinerycatalog.infrastructure.persistence.jpa.repositories;

import com.kipu.backend.Logistics.machinerycatalog.infrastructure.persistence.jpa.entities.MachineryCatalogJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineryCatalogJpaRepository extends JpaRepository<MachineryCatalogJpaEntity, String> {
}
