package com.kipu.backend.Logistics.interfaces.rest.transform;

import com.kipu.backend.Logistics.application.commandservices.SupplierCommandFailure;
import com.kipu.backend.Logistics.domain.model.aggregates.Supplier;
import com.kipu.backend.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

public class ResponseEntityFromSupplierUpdateCommandResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<Supplier, SupplierCommandFailure> result,
            MessageSource messageSource) {
        return result.fold(
                supplier -> ResponseEntity.ok(
                        SupplierResourceFromEntityAssembler.toResourceFromEntity(supplier)),
                failure -> {
                    var status = statusFromFailure(failure);
                    return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(
                            status,
                            localizedMessage(messageSource, failure.messageKey())));
                });
    }

    private static HttpStatus statusFromFailure(SupplierCommandFailure failure) {
        if (failure instanceof SupplierCommandFailure.NotFound) {
            return HttpStatus.NOT_FOUND;
        }
        if (failure instanceof SupplierCommandFailure.DuplicateRuc) {
            return HttpStatus.CONFLICT;
        }
        return HttpStatus.BAD_REQUEST;
    }

    private static String localizedMessage(MessageSource messageSource, String messageKey) {
        return messageSource.getMessage(messageKey, null, messageKey, LocaleContextHolder.getLocale());
    }
}
