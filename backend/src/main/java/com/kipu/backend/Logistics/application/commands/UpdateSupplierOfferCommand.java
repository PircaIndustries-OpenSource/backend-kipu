package com.kipu.backend.Logistics.application.commands;

import java.math.BigDecimal;

public record UpdateSupplierOfferCommand(BigDecimal unitPrice) {
    public UpdateSupplierOfferCommand {
        // unitPrice can be null for PATCH semantics
    }
}
