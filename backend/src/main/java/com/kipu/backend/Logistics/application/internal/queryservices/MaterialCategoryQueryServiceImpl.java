package com.kipu.backend.Logistics.application.internal.queryservices;

import com.kipu.backend.Logistics.application.queries.GetAllMaterialCategoriesQuery;
import com.kipu.backend.Logistics.application.queries.GetMaterialCategoryByIdQuery;
import com.kipu.backend.Logistics.application.queryservices.MaterialCategoryQueryService;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialCategory;
import com.kipu.backend.Logistics.domain.model.repositories.MaterialCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class MaterialCategoryQueryServiceImpl implements MaterialCategoryQueryService {

    private final MaterialCategoryRepository repository;

    public MaterialCategoryQueryServiceImpl(MaterialCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MaterialCategory> handle(GetAllMaterialCategoriesQuery query) {
        log.debug("Querying all material categories");
        // Note: Requires findAll() method in MaterialCategoryRepository
        var results = repository.findAll();
        log.debug("Found {} material category(s)", results.size());
        return results;
    }

    @Override
    public Optional<MaterialCategory> handle(GetMaterialCategoryByIdQuery query) {
        log.debug("Querying material category by id={}", query.id());
        var result = repository.findById(query.id());
        if (result.isEmpty()) log.debug("No material category found for id={}", query.id());
        return result;
    }
}