package com.kipu.backend.Logistics.machinerycatalog.domain.model.aggregates;

import com.kipu.backend.Logistics.machinerycatalog.domain.model.valueobjects.CatalogName;

import java.time.LocalDate;
import java.util.Objects;

public class MachineryCatalog {

    private final String id;
    private final CatalogName name;
    private final String brand;
    private final String model;
    private final String serialNumber;
    private final LocalDate acquisitionDate;

    public MachineryCatalog(String id, CatalogName name, String brand, String model,
                            String serialNumber, LocalDate acquisitionDate) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "machinerycatalog.error.name.notBlank");
        this.brand = brand;
        this.model = model;
        this.serialNumber = serialNumber;
        this.acquisitionDate = acquisitionDate;
    }

    public static MachineryCatalog create(CatalogName name, String brand, String model,
                                          String serialNumber, LocalDate acquisitionDate) {
        return new MachineryCatalog(null, name, brand, model, serialNumber, acquisitionDate);
    }

    public static MachineryCatalog rehydrate(String id, CatalogName name, String brand, String model,
                                             String serialNumber, LocalDate acquisitionDate) {
        return new MachineryCatalog(id, name, brand, model, serialNumber, acquisitionDate);
    }

    public String getId() { return id; }
    public CatalogName getName() { return name; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public String getSerialNumber() { return serialNumber; }
    public LocalDate getAcquisitionDate() { return acquisitionDate; }
}
