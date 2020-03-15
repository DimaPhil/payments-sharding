package com.yandex.payments.sharding.api.validator.payment;

import com.yandex.payments.sharding.api.validator.Validator;
import com.yandex.payments.sharding.dto.request.BatchPaymentRequestDto;

public class BatchPaymentRequestDtoValidator extends Validator<BatchPaymentRequestDto> {
    public BatchPaymentRequestDtoValidator(PaymentRequestDtoValidator paymentRequestDtoValidator) {
        addValidation(
            request -> request.getPayments() != null && request.getPayments().size() > 0,
            "Batch of payments should be present and be non-empty"
        );
        addValidation(
            request -> {
                request.getPayments().forEach(paymentRequestDtoValidator::validate);
                return true;
            },
            "All payments should pass validation"
        );
    }
}
