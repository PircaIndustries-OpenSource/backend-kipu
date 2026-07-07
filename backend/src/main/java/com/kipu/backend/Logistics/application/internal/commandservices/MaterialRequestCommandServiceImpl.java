package com.kipu.backend.Logistics.application.internal.commandservices;

import com.kipu.backend.Logistics.application.commandservices.MaterialRequestCommandFailure;
import com.kipu.backend.Logistics.application.commandservices.MaterialRequestCommandService;
import com.kipu.backend.Logistics.application.commands.CreateMaterialRequestCommand;
import com.kipu.backend.Logistics.application.commands.CreateMaterialRequestItemCommand;
import com.kipu.backend.Logistics.application.commands.UpdateMaterialRequestCommand;
import com.kipu.backend.Logistics.application.commands.UpdateMaterialRequestItemCommand;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialRequest;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialRequestItem;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialInventory;
import com.kipu.backend.Logistics.domain.model.repositories.MaterialRequestRepository;
import com.kipu.backend.Logistics.domain.model.repositories.MaterialInventoryRepository;
import com.kipu.backend.Logistics.domain.model.valueobjects.*;
import com.kipu.backend.Logistics.domain.model.valueobjects.external.ProjectId;
import com.kipu.backend.budget.domain.model.aggregates.Budget;
import com.kipu.backend.budget.domain.repositories.BudgetRepository;
import com.kipu.backend.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MaterialRequestCommandServiceImpl implements MaterialRequestCommandService {

    private final MaterialRequestRepository repository;
    private final BudgetRepository budgetRepository;
    private final MaterialInventoryRepository inventoryRepository;

    public MaterialRequestCommandServiceImpl(MaterialRequestRepository repository,
                                              BudgetRepository budgetRepository,
                                              MaterialInventoryRepository inventoryRepository) {
        this.repository = repository;
        this.budgetRepository = budgetRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    @Transactional
    public Result<MaterialRequest, MaterialRequestCommandFailure> handle(CreateMaterialRequestCommand command) {
        List<MaterialRequestItem> items = command.items().stream()
                .map(this::toDomainItem)
                .collect(Collectors.toList());

        var request = MaterialRequest.create(
                command.projectId(),
                command.deadline(),
                command.requestPriority(),
                command.deliveryLocation(),
                command.budgetLineId(),
                command.purpose(),
                command.additionalNotes(),
                command.requestedBy(),
                command.suggestedSupplierId(),
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

        var request = existing.get();
        var previousStatus = request.getRequestStatus();
        var updated = request.withStatus(requestStatus);
        var saved = repository.save(updated);

        if (requestStatus == RequestStatus.ACCEPTED && previousStatus != RequestStatus.ACCEPTED) {
            deductFromBudget(request);
            addToInventory(request);
        } else if (requestStatus != RequestStatus.ACCEPTED && previousStatus == RequestStatus.ACCEPTED) {
            restoreBudget(request);
            removeFromInventory(request);
        }

        log.info("Material request status updated: id={}, status={}", saved.getId(), saved.getRequestStatus());
        return Result.success(saved);
    }

    private void deductFromBudget(MaterialRequest request) {
        if (request.getBudgetLineId() == null) return;
        long budgetId = request.getBudgetLineId().value();
        Optional<Budget> budgetOpt = budgetRepository.findById(budgetId);
        if (budgetOpt.isEmpty()) {
            log.warn("Budget not found for deduction: budgetId={}, requestId={}", budgetId, request.getId());
            return;
        }
        Budget budget = budgetOpt.get();
        BigDecimal totalCost = request.getItems().stream()
                .map(MaterialRequestItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        double cost = totalCost.doubleValue();
        try {
            budget.addExpense("Material request #" + request.getId(), cost,
                    String.valueOf(request.getRequestedBy().value()),
                    "Auto-deducted on request acceptance");
            budgetRepository.save(budget);
            log.info("Budget deducted: budgetId={}, amount={}, requestId={}", budgetId, cost, request.getId());
        } catch (IllegalStateException e) {
            log.warn("Insufficient funds to deduct: budgetId={}, amount={}, available={}",
                    budgetId, cost, budget.getAvailable());
        }
    }

    private void restoreBudget(MaterialRequest request) {
        if (request.getBudgetLineId() == null) return;
        long budgetId = request.getBudgetLineId().value();
        Optional<Budget> budgetOpt = budgetRepository.findById(budgetId);
        if (budgetOpt.isEmpty()) return;
        Budget budget = budgetOpt.get();
        BigDecimal totalCost = request.getItems().stream()
                .map(MaterialRequestItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        double cost = totalCost.doubleValue();
        budget.addExtension(cost);
        budgetRepository.save(budget);
        log.info("Budget restored: budgetId={}, amount={}, requestId={}", budgetId, cost, request.getId());
    }

    private void addToInventory(MaterialRequest request) {
        if (request.getProjectId() == null) return;
        ProjectId projectId = new ProjectId(request.getProjectId());

        for (MaterialRequestItem item : request.getItems()) {
            MaterialCatalogId matId = item.getMaterialCatalogId();
            int qty = item.getQuantity().intValue();

            Optional<MaterialInventory> existing = inventoryRepository
                    .findByProjectIdAndMaterialCatalogId(projectId, matId);

            if (existing.isPresent()) {
                MaterialInventory inv = existing.get();
                int newStock = inv.getCurrentStock().value() + qty;
                inventoryRepository.save(inv.withStock(new Quantity(newStock)));
                log.info("Inventory updated: materialId={}, prevStock={}, added={}, newStock={}",
                        matId.value(), inv.getCurrentStock().value(), qty, newStock);
            } else {
                MaterialInventory inv = MaterialInventory.create(
                        projectId, matId,
                        new Quantity(qty), new Quantity(0),
                        new WarehouseLocation("A1-R1-S1"));
                inventoryRepository.save(inv);
                log.info("Inventory created: materialId={}, stock={}", matId.value(), qty);
            }
        }
    }

    private void removeFromInventory(MaterialRequest request) {
        if (request.getProjectId() == null) return;
        ProjectId projectId = new ProjectId(request.getProjectId());

        for (MaterialRequestItem item : request.getItems()) {
            MaterialCatalogId matId = item.getMaterialCatalogId();
            int qty = item.getQuantity().intValue();

            Optional<MaterialInventory> existing = inventoryRepository
                    .findByProjectIdAndMaterialCatalogId(projectId, matId);

            if (existing.isPresent()) {
                MaterialInventory inv = existing.get();
                int newStock = Math.max(0, inv.getCurrentStock().value() - qty);
                inventoryRepository.save(inv.withStock(new Quantity(newStock)));
                log.info("Inventory reduced: materialId={}, prevStock={}, removed={}, newStock={}",
                        matId.value(), inv.getCurrentStock().value(), qty, newStock);
            }
        }
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