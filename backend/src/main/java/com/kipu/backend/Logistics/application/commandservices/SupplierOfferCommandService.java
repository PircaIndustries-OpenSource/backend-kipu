package com.kipu.backend.Logistics.application.commandservices;

import com.kipu.backend.Logistics.application.commands.CreateSupplierOfferCommand;
import com.kipu.backend.Logistics.application.commands.UpdateSupplierOfferCommand;
import com.kipu.backend.Logistics.domain.model.aggregates.SupplierOffer;
import com.kipu.backend.shared.application.result.Result;

public interface SupplierOfferCommandService {
    Result<SupplierOffer, SupplierOfferCommandFailure> handle(CreateSupplierOfferCommand command);
    Result<SupplierOffer, SupplierOfferCommandFailure> handleUpdate(Long id, UpdateSupplierOfferCommand command);
    Result<Void, SupplierOfferCommandFailure> handleDelete(Long id);
}
