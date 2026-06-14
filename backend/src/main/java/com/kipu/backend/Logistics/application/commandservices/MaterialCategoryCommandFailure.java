package com.kipu.backend.Logistics.application.commandservices;

public sealed interface MaterialCategoryCommandFailure permits
        MaterialCategoryCommandFailure.Duplicate,
        MaterialCategoryCommandFailure.NotFound {

    String messageKey();

    record Duplicate() implements MaterialCategoryCommandFailure {
        @Override
        public String messageKey() {
            return "material.category.error.duplicate";
        }
    }

    record NotFound() implements MaterialCategoryCommandFailure {
        @Override
        public String messageKey() {
            return "material.category.error.notFound";
        }
    }
}