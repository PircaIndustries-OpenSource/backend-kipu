package com.kipu.backend.Logistics.application.internal.queryservices;

import com.kipu.backend.Logistics.application.queries.GetAllMaterialCatalogsQuery;
import com.kipu.backend.Logistics.application.queries.GetMaterialCatalogByIdQuery;
import com.kipu.backend.Logistics.application.queryservices.MaterialCatalogQueryService;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialCatalog;
import com.kipu.backend.Logistics.domain.model.repositories.MaterialCatalogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class MaterialCatalogQueryServiceImpl implements MaterialCatalogQueryService {

    private final MaterialCatalogRepository repository;

    public MaterialCatalogQueryServiceImpl(MaterialCatalogRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MaterialCatalog> handle(GetAllMaterialCatalogsQuery query) {
        log.debug("Querying all material catalogs");
        // Note: Requires findAll() method in MaterialCatalogRepository
        var results = repository.findAll();
        log.debug("Found {} material catalog(s)", results.size());
        return results;
    }

    @Override
    public Optional<MaterialCatalog> handle(GetMaterialCatalogByIdQuery query) {
        log.debug("Querying material catalog by id={}", query.id());
        var result = repository.findById(query.id());
        if (result.isEmpty()) log.debug("No material catalog found for id={}", query.id());
        return result;
    }
}