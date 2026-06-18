package com.kipu.backend.Logistics.application.internal.commandservices;

import com.kipu.backend.Logistics.application.commandservices.SupplierOfferCommandFailure;
import com.kipu.backend.Logistics.application.commandservices.SupplierOfferCommandService;
import com.kipu.backend.Logistics.application.commands.CreateSupplierOfferCommand;
import com.kipu.backend.Logistics.application.commands.UpdateSupplierOfferCommand;
import com.kipu.backend.Logistics.domain.model.aggregates.SupplierOffer;
import com.kipu.backend.Logistics.domain.model.repositories.SupplierOfferRepository;
import com.kipu.backend.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class SupplierOfferCommandServiceImpl implements SupplierOfferCommandService {

    private final SupplierOfferRepository repository;

    public SupplierOfferCommandServiceImpl(SupplierOfferRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Result<SupplierOffer, SupplierOfferCommandFailure> handle(CreateSupplierOfferCommand command) {
        var existing = repository.findBySupplierIdAndMaterialCatalogId(command.supplierId(), command.materialCatalogId());
        if (existing.isPresent()) {
            log.warn("Supplier offer already exists for supplierId={}, materialCatalogId={}",
                    command.supplierId().value(), command.materialCatalogId().value());
            return Result.failure(new SupplierOfferCommandFailure.Duplicate());
        }
        try {
            var offer = SupplierOffer.create(command.supplierId(), command.materialCatalogId(), command.unitPrice());
            var saved = repository.save(offer);
            log.info("Supplier offer created: id={}", saved.getId());
            return Result.success(saved);
        } catch (Exception e) {
            log.error("Error creating supplier offer", e);
            return Result.failure(new SupplierOfferCommandFailure.UpdateFailed());
        }
    }

    @Override
    @Transactional
    public Result<SupplierOffer, SupplierOfferCommandFailure> handleUpdate(Long id, UpdateSupplierOfferCommand command) {
        var existing = repository.findById(id);
        if (existing.isEmpty()) {
            log.warn("Supplier offer not found for update: id={}", id);
            return Result.failure(new SupplierOfferCommandFailure.NotFound());
        }
        try {
            var updated = existing.get().update(command.unitPrice());
            var saved = repository.save(updated);
            log.info("Supplier offer updated: id={}", saved.getId());
            return Result.success(saved);
        } catch (Exception e) {
            log.error("Error updating supplier offer id={}", id, e);
            return Result.failure(new SupplierOfferCommandFailure.UpdateFailed());
        }
    }

    @Override
    @Transactional
    public Result<Void, SupplierOfferCommandFailure> handleDelete(Long id) {
        var existing = repository.findById(id);
        if (existing.isEmpty()) {
            log.warn("Supplier offer not found for deletion: id={}", id);
            return Result.failure(new SupplierOfferCommandFailure.NotFound());
        }
        try {
            repository.deleteById(id);
            log.info("Supplier offer deleted: id={}", id);
            return Result.success(null);
        } catch (Exception e) {
            log.error("Error deleting supplier offer id={}", id, e);
            return Result.failure(new SupplierOfferCommandFailure.DeleteFailed());
        }
    }
}
