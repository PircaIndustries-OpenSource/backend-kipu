package com.kipu.backend.Logistics.machinerycatalog.application.internal.queryservices;

import com.kipu.backend.Logistics.machinerycatalog.application.queries.GetAllMachineryCatalogQuery;
import com.kipu.backend.Logistics.machinerycatalog.application.queries.GetMachineryCatalogByIdQuery;
import com.kipu.backend.Logistics.machinerycatalog.domain.model.aggregates.MachineryCatalog;

import java.util.List;
import java.util.Optional;

public interface MachineryCatalogQueryService {
    List<MachineryCatalog> handle(GetAllMachineryCatalogQuery query);
    Optional<MachineryCatalog> handle(GetMachineryCatalogByIdQuery query);
}
