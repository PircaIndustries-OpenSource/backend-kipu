package com.kipu.backend.Logistics.application.internal.commandservices;

import com.kipu.backend.Logistics.application.commandservices.MaterialCategoryCommandFailure;
import com.kipu.backend.Logistics.application.commandservices.MaterialCategoryCommandService;
import com.kipu.backend.Logistics.application.commands.CreateMaterialCategoryCommand;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialCategory;
import com.kipu.backend.Logistics.domain.model.repositories.MaterialCategoryRepository;
import com.kipu.backend.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class MaterialCategoryCommandServiceImpl implements MaterialCategoryCommandService {

    private static final String DUPLICATE_CONSTRAINT_NAME = "uk_material_category_name";
    private final MaterialCategoryRepository repository;

    public MaterialCategoryCommandServiceImpl(MaterialCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Result<MaterialCategory, MaterialCategoryCommandFailure> handle(CreateMaterialCategoryCommand command) {
        try {
            var category = MaterialCategory.create(command.name(), command.description(), command.isActive());
            var saved = repository.save(category);
            log.info("Material category created: id={}, name={}", saved.getId(), saved.getName().value());
            return Result.success(saved);
        } catch (DataIntegrityViolationException e) {
            if (isDuplicateConstraint(e)) {
                log.warn("Duplicate material category (name={})", command.name().value());
                return Result.failure(new MaterialCategoryCommandFailure.Duplicate());
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