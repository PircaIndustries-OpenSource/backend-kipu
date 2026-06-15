package com.kipu.backend.Logistics.application.commandservices;

import com.kipu.backend.Logistics.application.commands.CreateSupplierCommand;
import com.kipu.backend.Logistics.application.commands.UpdateSupplierCommand;
import com.kipu.backend.Logistics.domain.model.aggregates.Supplier;
import com.kipu.backend.shared.application.result.Result;

public interface SupplierCommandService {
    /**
     * Handles creation of a supplier.
     *
     * @param command create command containing RUC, social reason, contact, phone, email, payment terms and active flag
     * @return success when the supplier is created, failure when a supplier with the same RUC already exists
     */
    Result<Supplier, SupplierCommandFailure> handle(CreateSupplierCommand command);

    /**
     * Handles full update (PUT) of a supplier.
     *
     * @param id      the supplier ID
     * @param command update command with all mutable fields
     * @return success when the supplier is updated, failure when not found
     */
    Result<Supplier, SupplierCommandFailure> handleUpdate(Long id, UpdateSupplierCommand command);

    /**
     * Handles partial update (PATCH) of a supplier.
     * Null fields in the command mean "keep existing value".
     *
     * @param id      the supplier ID
     * @param command update command with fields to patch
     * @return success when the supplier is updated, failure when not found
     */
    Result<Supplier, SupplierCommandFailure> handlePatch(Long id, UpdateSupplierCommand command);
}