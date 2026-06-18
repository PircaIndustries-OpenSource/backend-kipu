package com.kipu.backend.Logistics.application.commandservices;

public sealed interface SupplierOfferCommandFailure permits
        SupplierOfferCommandFailure.Duplicate,
        SupplierOfferCommandFailure.NotFound,
        SupplierOfferCommandFailure.UpdateFailed,
        SupplierOfferCommandFailure.DeleteFailed {

    String messageKey();

    record Duplicate() implements SupplierOfferCommandFailure {
        @Override
        public String messageKey() { return "supplier.offer.error.duplicate"; }
    }

    record NotFound() implements SupplierOfferCommandFailure {
        @Override
        public String messageKey() { return "supplier.offer.error.notFound"; }
    }

    record UpdateFailed() implements SupplierOfferCommandFailure {
        @Override
        public String messageKey() { return "supplier.offer.error.notUpdated"; }
    }

    record DeleteFailed() implements SupplierOfferCommandFailure {
        @Override
        public String messageKey() { return "supplier.offer.error.notDeleted"; }
    }
}
