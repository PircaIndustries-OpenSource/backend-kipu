package com.kipu.backend.Logistics.application.commandservices;

public sealed interface MaterialInventoryCommandFailure permits
        MaterialInventoryCommandFailure.Duplicate,
        MaterialInventoryCommandFailure.NotFound {

    String messageKey();

    record Duplicate() implements MaterialInventoryCommandFailure {
        @Override
        public String messageKey() {
            return "material.inventory.error.duplicate";
        }
    }

    record NotFound() implements MaterialInventoryCommandFailure {
        @Override
        public String messageKey() {
            return "material.inventory.error.notFound";
        }
    }
}