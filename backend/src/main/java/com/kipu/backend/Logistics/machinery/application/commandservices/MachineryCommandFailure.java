package com.kipu.backend.Logistics.machinery.application.commandservices;

public sealed interface MachineryCommandFailure permits
        MachineryCommandFailure.NotFound,
        MachineryCommandFailure.UpdateFailed,
        MachineryCommandFailure.DeleteFailed {

    String messageKey();

    record NotFound() implements MachineryCommandFailure {
        @Override
        public String messageKey() {
            return "machinery.error.notFound";
        }
    }

    record UpdateFailed() implements MachineryCommandFailure {
        @Override
        public String messageKey() {
            return "machinery.error.notUpdated";
        }
    }

    record DeleteFailed() implements MachineryCommandFailure {
        @Override
        public String messageKey() {
            return "machinery.error.notDeleted";
        }
    }
}
