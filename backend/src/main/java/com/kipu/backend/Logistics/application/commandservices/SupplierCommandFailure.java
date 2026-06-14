package com.kipu.backend.Logistics.application.commandservices;

public sealed interface SupplierCommandFailure permits
        SupplierCommandFailure.DuplicateRuc,
        SupplierCommandFailure.NotFound,
        SupplierCommandFailure.UpdateFailed {

    String messageKey();

    record DuplicateRuc() implements SupplierCommandFailure {
        @Override
        public String messageKey() {
            return "supplier.error.duplicate.ruc";
        }
    }

    record NotFound() implements SupplierCommandFailure {
        @Override
        public String messageKey() {
            return "supplier.error.notFound";
        }
    }

    record UpdateFailed() implements SupplierCommandFailure {
        @Override
        public String messageKey() {
            return "supplier.error.notUpdated";
        }
    }
}