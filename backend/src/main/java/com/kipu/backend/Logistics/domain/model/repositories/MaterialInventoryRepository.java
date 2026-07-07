package com.kipu.backend.Logistics.domain.model.repositories;

import com.kipu.backend.Logistics.domain.model.aggregates.MaterialInventory;
import com.kipu.backend.Logistics.domain.model.valueobjects.CategoryId;
import com.kipu.backend.Logistics.domain.model.valueobjects.MaterialCatalogId;
import com.kipu.backend.Logistics.domain.model.valueobjects.external.ProjectId;

import java.util.List;
import java.util.Optional;

public interface MaterialInventoryRepository {
    MaterialInventory save(MaterialInventory materialInventory);
    Optional<MaterialInventory> findById(Long id);
    List<MaterialInventory> findByCategoryId(CategoryId categoryId);
    Optional<MaterialInventory> findByProjectIdAndMaterialCatalogId(ProjectId projectId, MaterialCatalogId materialCatalogId);
    List<MaterialInventory> findByProjectId(ProjectId projectId);
    List<MaterialInventory> findAll();
    boolean existsById(Long id);
    void deleteById(Long id);
}