package com.kipu.backend.Logistics.application.commandservices;

public sealed interface MaterialRequestCommandFailure permits
        MaterialRequestCommandFailure.InvalidBudget,
        MaterialRequestCommandFailure.NotFound,
        MaterialRequestCommandFailure.UpdateFailed {

    String messageKey();

    record InvalidBudget() implements MaterialRequestCommandFailure {
        @Override
        public String messageKey() {
            return "material.request.error.invalidBudget";
        }
    }

    record NotFound() implements MaterialRequestCommandFailure {
        @Override
        public String messageKey() {
            return "material.request.error.notFound";
        }
    }

    record UpdateFailed() implements MaterialRequestCommandFailure {
        @Override
        public String messageKey() {
            return "material.request.error.notUpdated";
        }
    }
}