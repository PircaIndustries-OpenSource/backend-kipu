package com.kipu.backend.Logistics.domain.model.repositories;

import com.kipu.backend.Logistics.domain.model.aggregates.MaterialInventory;
import com.kipu.backend.Logistics.domain.model.valueobjects.CategoryId;

import java.util.List;
import java.util.Optional;

public interface MaterialInventoryRepository {
    MaterialInventory save(MaterialInventory materialInventory);
    Optional<MaterialInventory> findById(Long id);
    List<MaterialInventory> findByCategoryId(CategoryId categoryId);
    List<MaterialInventory> findAll();
    boolean existsById(Long id);
    void deleteById(Long id);
}