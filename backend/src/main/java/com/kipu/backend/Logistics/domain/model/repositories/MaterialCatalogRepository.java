package com.kipu.backend.Logistics.domain.model.repositories;

import com.kipu.backend.Logistics.domain.model.aggregates.MaterialCatalog;
import com.kipu.backend.Logistics.domain.model.valueobjects.CategoryId;

import java.util.List;
import java.util.Optional;

public interface MaterialCatalogRepository {
    MaterialCatalog save(MaterialCatalog materialCatalog);
    Optional<MaterialCatalog> findById(Long id);
    boolean existsById(Long id);
    void deleteById(Long id);
    List<MaterialCatalog> findAll();
    List<MaterialCatalog> findByCategoryId(CategoryId categoryId);
}