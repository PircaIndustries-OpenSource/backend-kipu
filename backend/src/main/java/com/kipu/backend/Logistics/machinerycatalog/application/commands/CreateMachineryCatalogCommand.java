package com.kipu.backend.Logistics.machinerycatalog.application.commands;

import com.kipu.backend.Logistics.machinerycatalog.domain.model.valueobjects.CatalogName;

import java.time.LocalDate;

public record CreateMachineryCatalogCommand(
        CatalogName name,
        String brand,
        String model,
        String serialNumber,
        LocalDate acquisitionDate
) {
    public CreateMachineryCatalogCommand {
        if (name == null)
            throw new IllegalArgumentException("machinerycatalog.error.name.notBlank");
    }
}
