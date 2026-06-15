package com.kipu.backend.Logistics.application.commandservices;

import com.kipu.backend.Logistics.application.commands.CreateMaterialCategoryCommand;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialCategory;
import com.kipu.backend.shared.application.result.Result;

public interface MaterialCategoryCommandService {
    /**
     * Handles creation of a material category.
     *
     * @param command create command containing name, description and isActive
     * @return success when the material category is created, failure when a category with the same name already exists
     */
    Result<MaterialCategory, MaterialCategoryCommandFailure> handle(CreateMaterialCategoryCommand command);
}