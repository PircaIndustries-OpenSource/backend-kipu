package com.kipu.backend.Logistics.machinerycatalog.application.internal.commandservices;

import com.kipu.backend.Logistics.machinerycatalog.application.commands.CreateMachineryCatalogCommand;
import com.kipu.backend.Logistics.machinerycatalog.domain.model.aggregates.MachineryCatalog;

import java.util.Optional;

public interface MachineryCatalogCommandService {
    Optional<MachineryCatalog> handle(CreateMachineryCatalogCommand command);
    boolean handleDelete(String id);
}
