package com.kipu.backend.Logistics.machinerycatalog.domain.repositories;

import com.kipu.backend.Logistics.machinerycatalog.domain.model.aggregates.MachineryCatalog;

import java.util.List;
import java.util.Optional;

public interface MachineryCatalogRepository {
    MachineryCatalog save(MachineryCatalog catalog);
    Optional<MachineryCatalog> findById(String id);
    List<MachineryCatalog> findAll();
    boolean existsById(String id);
    void deleteById(String id);
}
