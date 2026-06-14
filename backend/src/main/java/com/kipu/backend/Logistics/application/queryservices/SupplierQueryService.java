package com.kipu.backend.Logistics.application.queryservices;

import com.kipu.backend.Logistics.application.queries.*;
import com.kipu.backend.Logistics.domain.model.aggregates.Supplier;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract providing read access to suppliers.
 *
 * @since 1.0.0
 */
public interface SupplierQueryService {

    /**
     * Retrieves a supplier by its identifier.
     *
     * @param query query containing the supplier identifier
     * @return supplier when found, otherwise empty
     * @throws IllegalArgumentException if id is null or negative
     */
    Optional<Supplier> handle(GetSupplierByIdQuery query);

    /**
     * Retrieves a supplier by its RUC.
     *
     * @param query query containing the RUC
     * @return supplier when found, otherwise empty
     * @throws IllegalArgumentException if ruc is null
     */
    Optional<Supplier> handle(GetSupplierByRucQuery query);

    /**
     * Retrieves all suppliers.
     *
     * @param query query for retrieving all suppliers
     * @return list of suppliers, possibly empty
     */
    List<Supplier> handle(GetAllSuppliersQuery query);

    /**
     * Retrieves suppliers by active status.
     *
     * @param query query containing the active flag
     * @return list of suppliers with the given active status, possibly empty
     * @throws IllegalArgumentException if isActive is null
     */
    List<Supplier> handle(GetSuppliersByIsActiveQuery query);
}