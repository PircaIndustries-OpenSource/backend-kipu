package com.kipu.backend.Logistics.domain.model.repositories;

import com.kipu.backend.Logistics.domain.model.aggregates.Supplier;
import com.kipu.backend.Logistics.domain.model.valueobjects.Ruc;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository {
    Supplier save(Supplier supplier);
    Optional<Supplier> findById(Long id);
    Optional<Supplier> findByRuc(Ruc ruc);
    List<Supplier> findByIsActive(Boolean isActive);
    List<Supplier> findAll();
    boolean existsById(Long id);
    boolean existsByRuc(Ruc ruc);
    void deleteById(Long id);
}