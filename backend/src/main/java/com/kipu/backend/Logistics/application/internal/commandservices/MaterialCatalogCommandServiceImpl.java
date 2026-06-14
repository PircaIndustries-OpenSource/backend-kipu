package com.kipu.backend.Logistics.application.internal.commandservices;

import com.kipu.backend.Logistics.application.commandservices.MaterialCatalogCommandFailure;
import com.kipu.backend.Logistics.application.commandservices.MaterialCatalogCommandService;
import com.kipu.backend.Logistics.application.commands.CreateMaterialCatalogCommand;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialCatalog;
import com.kipu.backend.Logistics.domain.model.repositories.MaterialCatalogRepository;
import com.kipu.backend.shared.infrastructure.documentation.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class MaterialCatalogCommandServiceImpl implements MaterialCatalogCommandService {

    private static final String DUPLICATE_CONSTRAINT_NAME = "uk_material_catalog_name";
    private final MaterialCatalogRepository repository;

    public MaterialCatalogCommandServiceImpl(MaterialCatalogRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Result<MaterialCatalog, MaterialCatalogCommandFailure> handle(CreateMaterialCatalogCommand command) {
        try {
            var catalog = MaterialCatalog.create(command.name(), command.categoryId(), command.measureUnit());
            var saved = repository.save(catalog);
            log.info("Material catalog created: id={}, name={}", saved.getId(), saved.getName().value());
            return Result.success(saved);
        } catch (DataIntegrityViolationException e) {
            if (isDuplicateConstraint(e)) {
                log.warn("Duplicate material catalog (name={})", command.name().value());
                return Result.failure(new MaterialCatalogCommandFailure.Duplicate());
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