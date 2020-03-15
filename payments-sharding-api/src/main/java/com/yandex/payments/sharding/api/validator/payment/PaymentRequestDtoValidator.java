package com.yandex.payments.sharding.api.validator.payment;

import com.yandex.payments.sharding.api.validator.Validator;
import com.yandex.payments.sharding.dto.request.PaymentRequestDto;

import java.math.BigDecimal;

public class PaymentRequestDtoValidator extends Validator<PaymentRequestDto> {
    public PaymentRequestDtoValidator() {
        addValidation(
            request -> request.getFromId() != null && request.getFromId() > 0,
            "Payer account id should be present and be greater zero"
        );
        addValidation(
            request -> request.getToId() != null && request.getToId() > 0,
            "Received account id should be present and be greater zero"
        );
        addValidation(
            request -> request.getAmount() != null && request.getAmount().compareTo(BigDecimal.ZERO) > 0,
            "Payment amount should be present abd be greater zero"
        );
        addValidation(
            request -> !request.getFromId().equals(request.getToId()),
            "Payer and received can't be the same person"
        );
    }
}
