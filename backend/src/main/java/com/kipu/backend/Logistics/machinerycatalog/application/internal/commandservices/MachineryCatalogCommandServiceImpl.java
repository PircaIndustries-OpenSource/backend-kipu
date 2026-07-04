package com.kipu.backend.Logistics.machinerycatalog.application.internal.commandservices;

import com.kipu.backend.Logistics.machinerycatalog.application.commands.CreateMachineryCatalogCommand;
import com.kipu.backend.Logistics.machinerycatalog.domain.model.aggregates.MachineryCatalog;
import com.kipu.backend.Logistics.machinerycatalog.domain.repositories.MachineryCatalogRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MachineryCatalogCommandServiceImpl implements MachineryCatalogCommandService {

    private final MachineryCatalogRepository repository;

    public MachineryCatalogCommandServiceImpl(MachineryCatalogRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Optional<MachineryCatalog> handle(CreateMachineryCatalogCommand command) {
        MachineryCatalog catalog = MachineryCatalog.create(
                command.name(),
                command.brand(),
                command.model(),
                command.serialNumber(),
                command.acquisitionDate()
        );
        return Optional.of(repository.save(catalog));
    }

    @Override
    @Transactional
    public boolean handleDelete(String id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
