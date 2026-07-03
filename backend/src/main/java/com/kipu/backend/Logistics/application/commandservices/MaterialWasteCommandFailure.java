package com.kipu.backend.Logistics.application.commandservices;

public sealed interface MaterialWasteCommandFailure permits
        MaterialWasteCommandFailure.NotFound,
        MaterialWasteCommandFailure.InvalidData {

    String messageKey();

    record NotFound() implements MaterialWasteCommandFailure {
        @Override
        public String messageKey() {
            return "material.waste.error.notFound";
        }
    }

    record InvalidData() implements MaterialWasteCommandFailure {
        @Override
        public String messageKey() {
            return "material.waste.error.invalidData";
        }
    }
}
