package com.kipu.backend.Logistics.application.internal.queryservices;

import com.kipu.backend.Logistics.application.queries.*;
import com.kipu.backend.Logistics.application.queryservices.MaterialRequestQueryService;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialRequest;
import com.kipu.backend.Logistics.domain.model.repositories.MaterialRequestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class MaterialRequestQueryServiceImpl implements MaterialRequestQueryService {

    private final MaterialRequestRepository repository;

    public MaterialRequestQueryServiceImpl(MaterialRequestRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<MaterialRequest> handle(GetMaterialRequestByIdQuery query) {
        log.debug("Querying material request by id={}", query.id());
        var result = repository.findById(query.id());
        if (result.isEmpty()) log.debug("No material request found for id={}", query.id());
        return result;
    }

    @Override
    public List<MaterialRequest> handle(GetAllMaterialRequestsQuery query) {
        log.debug("Querying all material requests");
        var results = repository.findAll();
        log.debug("Found {} material request(s)", results.size());
        return results;
    }

    @Override
    public List<MaterialRequest> handle(GetMaterialRequestsByStatusQuery query) {
        log.debug("Querying material requests by status={}", query.status());
        var results = repository.findByRequestStatus(query.status());
        log.debug("Found {} material request(s) with status={}", results.size(), query.status());
        return results;
    }
}