package com.kipu.backend.Logistics.machinerycatalog.application.internal.queryservices;

import com.kipu.backend.Logistics.machinerycatalog.application.queries.GetAllMachineryCatalogQuery;
import com.kipu.backend.Logistics.machinerycatalog.application.queries.GetMachineryCatalogByIdQuery;
import com.kipu.backend.Logistics.machinerycatalog.domain.model.aggregates.MachineryCatalog;
import com.kipu.backend.Logistics.machinerycatalog.domain.repositories.MachineryCatalogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MachineryCatalogQueryServiceImpl implements MachineryCatalogQueryService {

    private final MachineryCatalogRepository repository;

    public MachineryCatalogQueryServiceImpl(MachineryCatalogRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<MachineryCatalog> handle(GetAllMachineryCatalogQuery query) {
        return repository.findAll();
    }

    @Override
    public Optional<MachineryCatalog> handle(GetMachineryCatalogByIdQuery query) {
        return repository.findById(query.id());
    }
}
