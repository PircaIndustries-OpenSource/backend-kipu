package com.kipu.backend.Logistics.application.internal.queryservices;

import com.kipu.backend.Logistics.application.queries.GetAllMaterialInventoriesQuery;
import com.kipu.backend.Logistics.application.queries.GetMaterialInventoriesByCategoryIdQuery;
import com.kipu.backend.Logistics.application.queries.GetMaterialInventoryByIdQuery;
import com.kipu.backend.Logistics.application.queryservices.MaterialInventoryQueryService;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialInventory;
import com.kipu.backend.Logistics.domain.model.repositories.MaterialInventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class MaterialInventoryQueryServiceImpl implements MaterialInventoryQueryService {

    private final MaterialInventoryRepository repository;

    public MaterialInventoryQueryServiceImpl(MaterialInventoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MaterialInventory> handle(GetAllMaterialInventoriesQuery query) {
        log.debug("Querying all material inventories");
        var results = repository.findAll();
        log.debug("Found {} material inventory(s)", results.size());
        return results;
    }

    @Override
    public Optional<MaterialInventory> handle(GetMaterialInventoryByIdQuery query) {
        log.debug("Querying material inventory by id={}", query.id());
        var result = repository.findById(query.id());
        if (result.isEmpty()) log.debug("No material inventory found for id={}", query.id());
        return result;
    }

    @Override
    public List<MaterialInventory> handle(GetMaterialInventoriesByCategoryIdQuery query) {
        log.debug("Querying material inventories by categoryId={}", query.categoryId().value());
        var results = repository.findByCategoryId(query.categoryId());
        log.debug("Found {} material inventory(s) for categoryId={}", results.size(), query.categoryId().value());
        return results;
    }
}