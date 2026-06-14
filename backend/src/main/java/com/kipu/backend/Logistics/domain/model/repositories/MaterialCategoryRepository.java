package com.kipu.backend.Logistics.domain.model.repositories;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialCategory;

import java.util.List;
import java.util.Optional;

public interface MaterialCategoryRepository {
    MaterialCategory save(MaterialCategory materialCategory);
    Optional<MaterialCategory> findById(Long id);
    boolean existsById(Long id);
    void deleteById(Long id);
    List<MaterialCategory> findAll();
}