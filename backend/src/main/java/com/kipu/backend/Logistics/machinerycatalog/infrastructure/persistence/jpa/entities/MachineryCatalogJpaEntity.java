package com.kipu.backend.Logistics.machinerycatalog.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "machinery_catalog")
@Getter
@NoArgsConstructor
public class MachineryCatalogJpaEntity {

    @Id
    @Column(nullable = false, unique = true, updatable = false, length = 36)
    private String id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "brand", length = 100)
    private String brand;

    @Column(name = "model", length = 100)
    private String model;

    @Column(name = "serial_number", length = 100)
    private String serialNumber;

    @Column(name = "acquisition_date")
    private LocalDate acquisitionDate;

    public MachineryCatalogJpaEntity(String id, String name, String brand, String model,
                                     String serialNumber, LocalDate acquisitionDate) {
        this.id = id != null ? id : UUID.randomUUID().toString();
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.serialNumber = serialNumber;
        this.acquisitionDate = acquisitionDate;
    }
}
