package com.kipu.backend.Logistics.application.internal.commandservices;

import com.kipu.backend.Logistics.application.commandservices.SupplierCommandFailure;
import com.kipu.backend.Logistics.application.commandservices.SupplierCommandService;
import com.kipu.backend.Logistics.application.commands.CreateSupplierCommand;
import com.kipu.backend.Logistics.application.commands.UpdateSupplierCommand;
import com.kipu.backend.Logistics.domain.model.aggregates.Supplier;
import com.kipu.backend.Logistics.domain.model.repositories.SupplierRepository;
import com.kipu.backend.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class SupplierCommandServiceImpl implements SupplierCommandService {

    private static final String DUPLICATE_RUC_CONSTRAINT = "uk_supplier_ruc";
    private final SupplierRepository repository;

    public SupplierCommandServiceImpl(SupplierRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Result<Supplier, SupplierCommandFailure> handle(CreateSupplierCommand command) {
        if (repository.existsByRuc(command.ruc())) {
            log.warn("Supplier with RUC {} already exists", command.ruc().value());
            return Result.failure(new SupplierCommandFailure.DuplicateRuc());
        }

        try {
            var supplier = Supplier.create(
                    command.ruc(),
                    command.socialReason(),
                    command.contact(),
                    command.phone(),
                    command.email(),
                    command.paymentTerms(),
                    command.isActive()
            );
            var saved = repository.save(supplier);
            log.info("Supplier created: id={}, ruc={}", saved.getId(), saved.getRuc().value());
            return Result.success(saved);
        } catch (DataIntegrityViolationException e) {
            if (isDuplicateRucViolation(e)) {
                log.warn("Duplicate RUC detected via constraint: {}", command.ruc().value());
                return Result.failure(new SupplierCommandFailure.DuplicateRuc());
            }
            log.error("Unexpected data integrity violation", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public Result<Supplier, SupplierCommandFailure> handleUpdate(Long id, UpdateSupplierCommand command) {
        var existing = repository.findById(id);
        if (existing.isEmpty()) {
            log.warn("Supplier not found for update: id={}", id);
            return Result.failure(new SupplierCommandFailure.NotFound());
        }
        try {
            var updated = existing.get().update(
                    command.socialReason(),
                    command.contact(),
                    command.phone(),
                    command.email(),
                    command.paymentTerms(),
                    command.isActive()
            );
            var saved = repository.save(updated);
            log.info("Supplier updated: id={}", saved.getId());
            return Result.success(saved);
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation updating supplier id={}", id, e);
            return Result.failure(new SupplierCommandFailure.UpdateFailed());
        }
    }

    @Override
    @Transactional
    public Result<Supplier, SupplierCommandFailure> handlePatch(Long id, UpdateSupplierCommand command) {
        var existing = repository.findById(id);
        if (existing.isEmpty()) {
            log.warn("Supplier not found for patch: id={}", id);
            return Result.failure(new SupplierCommandFailure.NotFound());
        }
        try {
            var updated = existing.get().update(
                    command.socialReason(),
                    command.contact(),
                    command.phone(),
                    command.email(),
                    command.paymentTerms(),
                    command.isActive()
            );
            var saved = repository.save(updated);
            log.info("Supplier patched: id={}", saved.getId());
            return Result.success(saved);
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation patching supplier id={}", id, e);
            return Result.failure(new SupplierCommandFailure.UpdateFailed());
        }
    }

    private boolean isDuplicateRucViolation(DataIntegrityViolationException e) {
        Throwable cause = e;
        while (cause != null) {
            if (cause.getMessage() != null && cause.getMessage().contains(DUPLICATE_RUC_CONSTRAINT))
                return true;
            cause = cause.getCause();
        }
        return false;
    }
}