package com.kipu.backend.Logistics.application.queryservices;

import com.kipu.backend.Logistics.application.queries.GetAllMaterialInventoriesQuery;
import com.kipu.backend.Logistics.application.queries.GetMaterialInventoriesByCategoryIdQuery;
import com.kipu.backend.Logistics.application.queries.GetMaterialInventoryByIdQuery;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialInventory;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract providing read access to material inventories.
 *
 * @since 1.0.0
 */
public interface MaterialInventoryQueryService {

    /**
     * Retrieves all material inventories.
     *
     * @param query query for retrieving all inventories
     * @return list of material inventories, possibly empty
     */
    List<MaterialInventory> handle(GetAllMaterialInventoriesQuery query);

    /**
     * Retrieves a material inventory by its identifier.
     *
     * @param query query containing the inventory identifier
     * @return material inventory when found, otherwise empty
     * @throws IllegalArgumentException if id is null or negative
     */
    Optional<MaterialInventory> handle(GetMaterialInventoryByIdQuery query);

    /**
     * Retrieves material inventories by category identifier.
     *
     * @param query query containing the category identifier
     * @return list of material inventories for the given category, possibly empty
     * @throws IllegalArgumentException if categoryId is null
     */
    List<MaterialInventory> handle(GetMaterialInventoriesByCategoryIdQuery query);
}