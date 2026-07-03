package com.kipu.backend.Logistics.machinery.application.internal.queryservices;

import com.kipu.backend.Logistics.machinery.application.queries.GetMachineryByProjectIdQuery;
import com.kipu.backend.Logistics.machinery.application.queries.GetMachineryByIdQuery;
import com.kipu.backend.Logistics.machinery.application.queryservices.MachineryQueryService;
import com.kipu.backend.Logistics.machinery.domain.model.aggregates.Machinery;
import com.kipu.backend.Logistics.machinery.domain.repositories.MachineryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class MachineryQueryServiceImpl implements MachineryQueryService {

    private final MachineryRepository repository;

    public MachineryQueryServiceImpl(MachineryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Machinery> handle(GetMachineryByProjectIdQuery query) {
        log.debug("Querying machinery by projectId={}", query.projectId());
        var results = repository.findByProjectId(query.projectId());
        log.debug("Found {} machinery records", results.size());
        return results;
    }

    @Override
    public Optional<Machinery> handle(GetMachineryByIdQuery query) {
        log.debug("Querying machinery by id={}", query.id());
        var result = repository.findById(query.id());
        if (result.isEmpty()) {
            log.debug("No machinery found for id={}", query.id());
        }
        return result;
    }
}
