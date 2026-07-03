package com.kipu.backend.Logistics.application.queries;

/**
 * Query to retrieve a material waste record by its identifier.
 *
 * @param id the waste record identifier (must be positive)
 */
public record GetMaterialWasteByIdQuery(Long id) {
    public GetMaterialWasteByIdQuery {
        if (id == null || id <= 0) throw new IllegalArgumentException("id must be a positive number");
    }
}
