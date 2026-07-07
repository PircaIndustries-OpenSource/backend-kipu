package com.kipu.backend.Logistics.application.internal.commandservices;

import com.kipu.backend.Logistics.application.commandservices.MaterialWasteCommandFailure;
import com.kipu.backend.Logistics.application.commandservices.MaterialWasteCommandService;
import com.kipu.backend.Logistics.application.commands.CreateMaterialWasteCommand;
import com.kipu.backend.Logistics.application.commands.DeleteMaterialWasteCommand;
import com.kipu.backend.Logistics.application.commands.UpdateMaterialWasteCommand;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialInventory;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialWaste;
import com.kipu.backend.Logistics.domain.model.repositories.MaterialInventoryRepository;
import com.kipu.backend.Logistics.domain.model.repositories.MaterialWasteRepository;
import com.kipu.backend.Logistics.domain.model.valueobjects.Quantity;
import com.kipu.backend.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class MaterialWasteCommandServiceImpl implements MaterialWasteCommandService {

    private final MaterialWasteRepository repository;
    private final MaterialInventoryRepository inventoryRepository;

    public MaterialWasteCommandServiceImpl(MaterialWasteRepository repository,
                                           MaterialInventoryRepository inventoryRepository) {
        this.repository = repository;
        this.inventoryRepository = inventoryRepository;
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

        decreaseInventory(command.projectId(), command.materialCatalogId(), command.quantity());

        return Result.success(saved);
    }

    private void decreaseInventory(
            com.kipu.backend.Logistics.domain.model.valueobjects.external.ProjectId projectId,
            com.kipu.backend.Logistics.domain.model.valueobjects.MaterialCatalogId materialCatalogId,
            Quantity wasteQuantity) {
        try {
            var opt = inventoryRepository.findByProjectIdAndMaterialCatalogId(projectId, materialCatalogId);
            if (opt.isEmpty()) {
                log.warn("No inventory found for project={}, material={}, cannot decrease stock",
                        projectId.value(), materialCatalogId.value());
                return;
            }
            var inventory = opt.get();
            var newStock = Math.max(0, inventory.getCurrentStock().value() - wasteQuantity.value());
            var updated = MaterialInventory.rehydrate(
                    inventory.getId(),
                    inventory.getProjectId(),
                    inventory.getMaterialCatalogId(),
                    new Quantity(newStock),
                    inventory.getMinimumStock(),
                    inventory.getLocation(),
                    inventory.getCreatedAt(),
                    inventory.getUpdatedAt()
            );
            inventoryRepository.save(updated);
            log.info("Inventory decreased for project={}, material={}: {} -> {}",
                    projectId.value(), materialCatalogId.value(),
                    inventory.getCurrentStock().value(), newStock);
        } catch (Exception e) {
            log.error("Failed to decrease inventory for project={}, material={}: {}",
                    projectId.value(), materialCatalogId.value(), e.getMessage());
        }
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
