package com.kipu.backend.Logistics.interfaces.rest.transform;

import com.kipu.backend.Logistics.application.commandservices.SupplierOfferCommandFailure;
import com.kipu.backend.Logistics.domain.model.aggregates.SupplierOffer;
import com.kipu.backend.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.CREATED;

public class ResponseEntityFromSupplierOfferCommandResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<SupplierOffer, SupplierOfferCommandFailure> result,
            MessageSource messageSource) {
        return result.fold(
                offer -> new ResponseEntity<>(
                        SupplierOfferResourceFromEntityAssembler.toResourceFromEntity(offer),
                        CREATED),
                failure -> {
                    var status = statusFromFailure(failure);
                    return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(
                            status,
                            localizedMessage(messageSource, failure.messageKey())));
                });
    }

    private static HttpStatus statusFromFailure(SupplierOfferCommandFailure failure) {
        if (failure instanceof SupplierOfferCommandFailure.NotFound) {
            return HttpStatus.NOT_FOUND;
        }
        if (failure instanceof SupplierOfferCommandFailure.Duplicate) {
            return HttpStatus.CONFLICT;
        }
        return HttpStatus.BAD_REQUEST;
    }

    private static String localizedMessage(MessageSource messageSource, String messageKey) {
        return messageSource.getMessage(messageKey, null, messageKey, LocaleContextHolder.getLocale());
    }
}
