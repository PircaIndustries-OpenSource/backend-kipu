package com.kipu.backend.Logistics.application.queryservices;

import com.kipu.backend.Logistics.application.queries.*;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialRequest;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract providing read access to material requests.
 *
 * @since 1.0.0
 */
public interface MaterialRequestQueryService {

    /**
     * Retrieves a material request by its identifier.
     *
     * @param query query containing the request identifier
     * @return material request with items when found, otherwise empty
     * @throws IllegalArgumentException if id is null or negative
     */
    Optional<MaterialRequest> handle(GetMaterialRequestByIdQuery query);

    /**
     * Retrieves all material requests.
     *
     * @param query query for retrieving all requests
     * @return list of material requests with items, possibly empty
     */
    List<MaterialRequest> handle(GetAllMaterialRequestsQuery query);

    /**
     * Retrieves material requests by status.
     *
     * @param query query containing the request status
     * @return list of material requests with the given status, possibly empty
     * @throws IllegalArgumentException if status is null
     */
    List<MaterialRequest> handle(GetMaterialRequestsByStatusQuery query);
}