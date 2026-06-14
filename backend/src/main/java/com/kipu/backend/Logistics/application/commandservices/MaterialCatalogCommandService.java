package com.kipu.backend.Logistics.application.commandservices;

import com.kipu.backend.Logistics.application.commands.CreateMaterialCatalogCommand;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialCatalog;
import com.kipu.backend.shared.infrastructure.documentation.application.result.Result;

/**
 * Application service contract for material catalog command operations.
 */
public interface MaterialCatalogCommandService {
    /**
     * Handles creation of a material catalog.
     *
     * @param command create command containing name, categoryId and measureUnit
     * @return success when the material catalog is created, failure when it already exists
     */
    Result<MaterialCatalog, MaterialCatalogCommandFailure> handle(CreateMaterialCatalogCommand command);
}