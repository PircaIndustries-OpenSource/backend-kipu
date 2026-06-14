package com.kipu.backend.Logistics.application.internal.queryservices;

import com.kipu.backend.Logistics.application.queries.*;
import com.kipu.backend.Logistics.application.queryservices.SupplierQueryService;
import com.kipu.backend.Logistics.domain.model.aggregates.Supplier;
import com.kipu.backend.Logistics.domain.model.repositories.SupplierRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class SupplierQueryServiceImpl implements SupplierQueryService {

    private final SupplierRepository repository;

    public SupplierQueryServiceImpl(SupplierRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Supplier> handle(GetSupplierByIdQuery query) {
        log.debug("Querying supplier by id={}", query.id());
        var result = repository.findById(query.id());
        if (result.isEmpty()) log.debug("No supplier found for id={}", query.id());
        return result;
    }

    @Override
    public Optional<Supplier> handle(GetSupplierByRucQuery query) {
        log.debug("Querying supplier by ruc={}", mask(query.ruc().value()));
        var result = repository.findByRuc(query.ruc());
        if (result.isEmpty()) log.debug("No supplier found for ruc={}", mask(query.ruc().value()));
        return result;
    }

    @Override
    public List<Supplier> handle(GetAllSuppliersQuery query) {
        log.debug("Querying all suppliers");
        var results = repository.findAll();
        log.debug("Found {} supplier(s)", results.size());
        return results;
    }

    @Override
    public List<Supplier> handle(GetSuppliersByIsActiveQuery query) {
        log.debug("Querying suppliers by isActive={}", query.isActive());
        var results = repository.findByIsActive(query.isActive());
        log.debug("Found {} supplier(s) with isActive={}", results.size(), query.isActive());
        return results;
    }

    /**
     * Returns a masked representation of a secret value, exposing only the last four characters.
     *
     * @param value the raw secret string to mask
     * @return masked string safe for log output
     */
    private static String mask(String value) {
        if (value == null || value.length() <= 4) return "****";
        return "****" + value.substring(value.length() - 4);
    }
}