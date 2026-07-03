package com.kipu.backend.Logistics.application.internal.queryservices;

import com.kipu.backend.Logistics.application.queries.GetAllMaterialWastesQuery;
import com.kipu.backend.Logistics.application.queries.GetMaterialWasteByIdQuery;
import com.kipu.backend.Logistics.application.queries.GetMaterialWastesByProjectIdQuery;
import com.kipu.backend.Logistics.application.queryservices.MaterialWasteQueryService;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialWaste;
import com.kipu.backend.Logistics.domain.model.repositories.MaterialWasteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class MaterialWasteQueryServiceImpl implements MaterialWasteQueryService {

    private final MaterialWasteRepository repository;

    public MaterialWasteQueryServiceImpl(MaterialWasteRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MaterialWaste> handle(GetAllMaterialWastesQuery query) {
        log.debug("Querying all material wastes");
        var results = repository.findAll();
        log.debug("Found {} material waste(s)", results.size());
        return results;
    }

    @Override
    public Optional<MaterialWaste> handle(GetMaterialWasteByIdQuery query) {
        log.debug("Querying material waste by id={}", query.id());
        var result = repository.findById(query.id());
        if (result.isEmpty()) log.debug("No material waste found for id={}", query.id());
        return result;
    }

    @Override
    public List<MaterialWaste> handle(GetMaterialWastesByProjectIdQuery query) {
        log.debug("Querying material wastes by projectId={}", query.projectId().value());
        var results = repository.findByProjectId(query.projectId());
        log.debug("Found {} material waste(s) for projectId={}", results.size(), query.projectId().value());
        return results;
    }
}
