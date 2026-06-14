package com.kipu.backend.Logistics.application.queryservices;

import com.kipu.backend.Logistics.application.queries.GetAllMaterialCategoriesQuery;
import com.kipu.backend.Logistics.application.queries.GetMaterialCategoryByIdQuery;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialCategory;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract providing read access to material categories.
 *
 * @since 1.0.0
 */
public interface MaterialCategoryQueryService {

    /**
     * Retrieves all material categories.
     *
     * @param query query for retrieving all categories
     * @return list of material categories, possibly empty
     */
    List<MaterialCategory> handle(GetAllMaterialCategoriesQuery query);

    /**
     * Retrieves a material category by its identifier.
     *
     * @param query query containing the category identifier
     * @return material category when found, otherwise empty
     * @throws IllegalArgumentException if id is null or negative
     */
    Optional<MaterialCategory> handle(GetMaterialCategoryByIdQuery query);
}