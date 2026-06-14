package com.kipu.backend.Logistics.application.commandservices;

/**
 * Failure types for material catalog command execution.
 */
public sealed interface MaterialCatalogCommandFailure permits
        MaterialCatalogCommandFailure.Duplicate,
        MaterialCatalogCommandFailure.NotFound {

    String messageKey();

    record Duplicate() implements MaterialCatalogCommandFailure {
        @Override
        public String messageKey() {
            return "material.catalog.error.duplicate";
        }
    }

    record NotFound() implements MaterialCatalogCommandFailure {
        @Override
        public String messageKey() {
            return "material.catalog.error.notFound";
        }
    }
}