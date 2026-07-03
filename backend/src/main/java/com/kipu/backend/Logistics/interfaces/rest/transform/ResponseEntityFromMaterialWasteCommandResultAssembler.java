package com.kipu.backend.Logistics.interfaces.rest.transform;

import com.kipu.backend.Logistics.application.commandservices.MaterialWasteCommandFailure;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialWaste;
import com.kipu.backend.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.CREATED;

public class ResponseEntityFromMaterialWasteCommandResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<MaterialWaste, MaterialWasteCommandFailure> result,
            MessageSource messageSource) {
        return result.fold(
                waste -> new ResponseEntity<>(
                        MaterialWasteResourceFromEntityAssembler.toResourceFromEntity(waste),
                        CREATED),
                failure -> {
                    var status = statusFromFailure(failure);
                    return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(
                            status,
                            localizedMessage(messageSource, failure.messageKey())));
                });
    }

    public static ResponseEntity<?> toResponseEntityFromUpdateResult(
            Result<MaterialWaste, MaterialWasteCommandFailure> result,
            MessageSource messageSource) {
        return result.fold(
                waste -> ResponseEntity.ok(
                        MaterialWasteResourceFromEntityAssembler.toResourceFromEntity(waste)),
                failure -> {
                    var status = statusFromFailure(failure);
                    return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(
                            status,
                            localizedMessage(messageSource, failure.messageKey())));
                });
    }

    public static ResponseEntity<?> toResponseEntityFromDeleteResult(
            Result<Long, MaterialWasteCommandFailure> result,
            MessageSource messageSource) {
        return result.fold(
                id -> ResponseEntity.noContent().build(),
                failure -> {
                    var status = statusFromFailure(failure);
                    return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(
                            status,
                            localizedMessage(messageSource, failure.messageKey())));
                });
    }

    private static HttpStatus statusFromFailure(MaterialWasteCommandFailure failure) {
        if (failure instanceof MaterialWasteCommandFailure.NotFound) {
            return HttpStatus.NOT_FOUND;
        }
        return HttpStatus.BAD_REQUEST;
    }

    private static String localizedMessage(MessageSource messageSource, String messageKey) {
        return messageSource.getMessage(messageKey, null, messageKey, LocaleContextHolder.getLocale());
    }
}
