package com.kipu.backend.Logistics.application.internal.commandservices;

import com.kipu.backend.Logistics.application.commandservices.MaterialRequestCommandFailure;
import com.kipu.backend.Logistics.application.commandservices.MaterialRequestCommandService;
import com.kipu.backend.Logistics.application.commands.CreateMaterialRequestCommand;
import com.kipu.backend.Logistics.application.commands.CreateMaterialRequestItemCommand;
import com.kipu.backend.Logistics.application.commands.UpdateMaterialRequestCommand;
import com.kipu.backend.Logistics.application.commands.UpdateMaterialRequestItemCommand;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialRequest;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialRequestItem;
import com.kipu.backend.Logistics.domain.model.repositories.MaterialRequestRepository;
import com.kipu.backend.Logistics.domain.model.valueobjects.RequestStatus;
import com.kipu.backend.shared.infrastructure.documentation.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MaterialRequestCommandServiceImpl implements MaterialRequestCommandService {

    private final MaterialRequestRepository repository;

    public MaterialRequestCommandServiceImpl(MaterialRequestRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Result<MaterialRequest, MaterialRequestCommandFailure> handle(CreateMaterialRequestCommand command) {
        List<MaterialRequestItem> items = command.items().stream()
                .map(this::toDomainItem)
                .collect(Collectors.toList());

        var request = MaterialRequest.create(
                command.deadline(),
                command.requestPriority(),
                command.deliveryLocation(),
                command.budgetLineId(),
                command.purpose(),
                command.additionalNotes(),
                command.requestedBy(),
                items
        );
        var saved = repository.save(request);
        log.info("Material request created: id={}, status={}", saved.getId(), saved.getRequestStatus());
        return Result.success(saved);
    }

    @Override
    @Transactional
    public Result<MaterialRequest, MaterialRequestCommandFailure> handleUpdate(Long id, UpdateMaterialRequestCommand command) {
        var existing = repository.findById(id);
        if (existing.isEmpty()) {
            log.warn("Material request not found for update: id={}", id);
            return Result.failure(new MaterialRequestCommandFailure.NotFound());
        }
        List<MaterialRequestItem> items = command.items().stream()
                .map(this::toDomainItemFromUpdate)
                .collect(Collectors.toList());
        var updated = existing.get().update(
                command.deadline(),
                command.requestPriority(),
                command.deliveryLocation(),
                command.purpose(),
                command.additionalNotes(),
                items
        );
        var saved = repository.save(updated);
        log.info("Material request updated: id={}", saved.getId());
        return Result.success(saved);
    }

    @Override
    @Transactional
    public Result<MaterialRequest, MaterialRequestCommandFailure> handlePatch(Long id, UpdateMaterialRequestCommand command) {
        var existing = repository.findById(id);
        if (existing.isEmpty()) {
            log.warn("Material request not found for patch: id={}", id);
            return Result.failure(new MaterialRequestCommandFailure.NotFound());
        }
        List<MaterialRequestItem> items = command.items() != null
                ? command.items().stream().map(this::toDomainItemFromUpdate).collect(Collectors.toList())
                : null;
        var updated = existing.get().update(
                command.deadline(),
                command.requestPriority(),
                command.deliveryLocation(),
                command.purpose(),
                command.additionalNotes(),
                items
        );
        var saved = repository.save(updated);
        log.info("Material request patched: id={}", saved.getId());
        return Result.success(saved);
    }

    @Override
    @Transactional
    public Result<MaterialRequest, MaterialRequestCommandFailure> handleStatusUpdate(Long id, String status) {
        var existing = repository.findById(id);
        if (existing.isEmpty()) {
            log.warn("Material request not found for status update: id={}", id);
            return Result.failure(new MaterialRequestCommandFailure.NotFound());
        }
        RequestStatus requestStatus;
        try {
            requestStatus = RequestStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.warn("Invalid status value: {}", status);
            return Result.failure(new MaterialRequestCommandFailure.UpdateFailed());
        }
        var updated = existing.get().withStatus(requestStatus);
        var saved = repository.save(updated);
        log.info("Material request status updated: id={}, status={}", saved.getId(), saved.getRequestStatus());
        return Result.success(saved);
    }

    private MaterialRequestItem toDomainItem(CreateMaterialRequestItemCommand cmd) {
        return MaterialRequestItem.create(
                cmd.materialCatalogId(),
                cmd.supplierId(),
                cmd.quantity(),
                cmd.unitPrice()
        );
    }

    private MaterialRequestItem toDomainItemFromUpdate(UpdateMaterialRequestItemCommand cmd) {
        return MaterialRequestItem.create(
                cmd.materialCatalogId(),
                cmd.supplierId(),
                cmd.quantity(),
                cmd.unitPrice()
        );
    }
}