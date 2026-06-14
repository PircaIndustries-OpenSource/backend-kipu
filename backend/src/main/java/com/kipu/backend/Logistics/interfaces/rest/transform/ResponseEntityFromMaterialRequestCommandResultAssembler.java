package com.kipu.backend.Logistics.interfaces.rest.transform;

import com.kipu.backend.Logistics.application.commandservices.MaterialRequestCommandFailure;
import com.kipu.backend.Logistics.domain.model.aggregates.MaterialRequest;
import com.kipu.backend.shared.infrastructure.documentation.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.CREATED;

public class ResponseEntityFromMaterialRequestCommandResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<MaterialRequest, MaterialRequestCommandFailure> result,
            MessageSource messageSource) {
        return result.fold(
                request -> new ResponseEntity<>(
                        MaterialRequestResourceFromEntityAssembler.toResourceFromEntity(request),
                        CREATED),
                failure -> {
                    var status = statusFromFailure(failure);
                    return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(
                            status,
                            localizedMessage(messageSource, failure.messageKey())));
                });
    }

    private static HttpStatus statusFromFailure(MaterialRequestCommandFailure failure) {
        if (failure instanceof MaterialRequestCommandFailure.InvalidBudget) {
            return HttpStatus.PAYMENT_REQUIRED; // o BAD_REQUEST si prefieres
        }
        return HttpStatus.BAD_REQUEST;
    }

    private static String localizedMessage(MessageSource messageSource, String messageKey) {
        return messageSource.getMessage(messageKey, null, messageKey, LocaleContextHolder.getLocale());
    }
}