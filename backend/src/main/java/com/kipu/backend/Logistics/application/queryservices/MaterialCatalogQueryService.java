package com.kipu.backend.Logistics.application.queryservices;

import com.kipu.backend.Logistics.application.queries.GetAllMaterialCatalogsQuery;
import com.kipu.backend.Logistics.application.queries.GetMaterialCatalogByIdQuery;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialCatalog;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract providing read access to material catalogs.
 * It exposes query operations used by the interface layer to retrieve material
 * catalogs without leaking persistence details.
 *
 * @since 1.0.0
 */
public interface MaterialCatalogQueryService {

    /**
     * Retrieves all material catalogs.
     *
     * @param query query for retrieving all catalogs
     * @return list of material catalogs, possibly empty
     */
    List<MaterialCatalog> handle(GetAllMaterialCatalogsQuery query);

    /**
     * Retrieves a material catalog by its identifier.
     *
     * @param query query containing the catalog identifier
     * @return material catalog when found, otherwise empty
     * @throws IllegalArgumentException if id is null or negative
     */
    Optional<MaterialCatalog> handle(GetMaterialCatalogByIdQuery query);
}