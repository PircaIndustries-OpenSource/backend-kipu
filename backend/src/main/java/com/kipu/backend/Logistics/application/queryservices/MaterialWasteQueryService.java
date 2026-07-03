package com.kipu.backend.Logistics.application.queryservices;

import com.kipu.backend.Logistics.application.queries.GetAllMaterialWastesQuery;
import com.kipu.backend.Logistics.application.queries.GetMaterialWasteByIdQuery;
import com.kipu.backend.Logistics.application.queries.GetMaterialWastesByProjectIdQuery;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialWaste;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract providing read access to material waste records.
 *
 * @since 1.0.0
 */
public interface MaterialWasteQueryService {

    /**
     * Retrieves all material waste records.
     *
     * @param query query for retrieving all waste records
     * @return list of material wastes, possibly empty
     */
    List<MaterialWaste> handle(GetAllMaterialWastesQuery query);

    /**
     * Retrieves a material waste record by its identifier.
     *
     * @param query query containing the waste record identifier
     * @return material waste when found, otherwise empty
     */
    Optional<MaterialWaste> handle(GetMaterialWasteByIdQuery query);

    /**
     * Retrieves all material waste records for a given project.
     *
     * @param query query containing the project identifier
     * @return list of material wastes for the project, possibly empty
     */
    List<MaterialWaste> handle(GetMaterialWastesByProjectIdQuery query);
}
