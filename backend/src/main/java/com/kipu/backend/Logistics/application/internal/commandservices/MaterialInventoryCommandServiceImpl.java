package com.kipu.backend.Logistics.application.internal.commandservices;

import com.kipu.backend.Logistics.application.commandservices.MaterialInventoryCommandFailure;
import com.kipu.backend.Logistics.application.commandservices.MaterialInventoryCommandService;
import com.kipu.backend.Logistics.application.commands.CreateMaterialInventoryCommand;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialInventory;
import com.kipu.backend.Logistics.domain.model.repositories.MaterialInventoryRepository;
import com.kipu.backend.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class MaterialInventoryCommandServiceImpl implements MaterialInventoryCommandService {

    private static final String DUPLICATE_CONSTRAINT_NAME = "uk_material_inventory_project_material";
    private final MaterialInventoryRepository repository;

    public MaterialInventoryCommandServiceImpl(MaterialInventoryRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Result<MaterialInventory, MaterialInventoryCommandFailure> handle(CreateMaterialInventoryCommand command) {
        try {
            var inventory = MaterialInventory.create(
                    command.projectId(),
                    command.materialCatalogId(),
                    command.currentStock(),
                    command.minimumStock(),
                    command.location()
            );
            var saved = repository.save(inventory);
            log.info("Material inventory created: id={}, project={}, material={}",
                    saved.getId(), saved.getProjectId().value(), saved.getMaterialCatalogId().value());
            return Result.success(saved);
        } catch (DataIntegrityViolationException e) {
            if (isDuplicateConstraint(e)) {
                log.warn("Duplicate material inventory (project={}, material={})",
                        command.projectId().value(), command.materialCatalogId().value());
                return Result.failure(new MaterialInventoryCommandFailure.Duplicate());
            }
            log.error("Unexpected data integrity violation", e);
            throw e;
        }
    }

    private boolean isDuplicateConstraint(DataIntegrityViolationException e) {
        Throwable cause = e;
        while (cause != null) {
            if (cause.getMessage() != null && cause.getMessage().contains(DUPLICATE_CONSTRAINT_NAME))
                return true;
            cause = cause.getCause();
        }
        return false;
    }
}