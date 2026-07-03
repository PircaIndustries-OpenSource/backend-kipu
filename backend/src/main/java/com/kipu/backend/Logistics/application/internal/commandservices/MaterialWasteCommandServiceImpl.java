package com.kipu.backend.Logistics.application.internal.commandservices;

import com.kipu.backend.Logistics.application.commandservices.MaterialWasteCommandFailure;
import com.kipu.backend.Logistics.application.commandservices.MaterialWasteCommandService;
import com.kipu.backend.Logistics.application.commands.CreateMaterialWasteCommand;
import com.kipu.backend.Logistics.application.commands.DeleteMaterialWasteCommand;
import com.kipu.backend.Logistics.application.commands.UpdateMaterialWasteCommand;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialWaste;
import com.kipu.backend.Logistics.domain.model.repositories.MaterialWasteRepository;
import com.kipu.backend.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class MaterialWasteCommandServiceImpl implements MaterialWasteCommandService {

    private final MaterialWasteRepository repository;

    public MaterialWasteCommandServiceImpl(MaterialWasteRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Result<MaterialWaste, MaterialWasteCommandFailure> handle(CreateMaterialWasteCommand command) {
        var waste = MaterialWaste.create(
                command.projectId(),
                command.materialCatalogId(),
                command.quantity(),
                command.unit(),
                command.classificationType(),
                command.date(),
                command.description(),
                command.reportedBy(),
                command.photoUrl()
        );
        var saved = repository.save(waste);
        log.info("Material waste created: id={}, project={}, material={}, type={}",
                saved.getId(), saved.getProjectId().value(),
                saved.getMaterialCatalogId().value(), saved.getClassificationType());
        return Result.success(saved);
    }

    @Override
    @Transactional
    public Result<MaterialWaste, MaterialWasteCommandFailure> handle(UpdateMaterialWasteCommand command) {
        if (!repository.existsById(command.id())) {
            log.warn("Material waste not found for update: id={}", command.id());
            return Result.failure(new MaterialWasteCommandFailure.NotFound());
        }
        var waste = MaterialWaste.rehydrate(
                command.id(),
                command.projectId(),
                command.materialCatalogId(),
                command.quantity(),
                command.unit(),
                command.classificationType(),
                command.date(),
                command.description(),
                command.reportedBy(),
                command.photoUrl(),
                null,  // createdAt managed by JPA auditing
                null   // updatedAt managed by JPA auditing
        );
        var saved = repository.save(waste);
        log.info("Material waste updated: id={}", saved.getId());
        return Result.success(saved);
    }

    @Override
    @Transactional
    public Result<Long, MaterialWasteCommandFailure> handle(DeleteMaterialWasteCommand command) {
        if (!repository.existsById(command.id())) {
            log.warn("Material waste not found for deletion: id={}", command.id());
            return Result.failure(new MaterialWasteCommandFailure.NotFound());
        }
        repository.deleteById(command.id());
        log.info("Material waste deleted: id={}", command.id());
        return Result.success(command.id());
    }
}
