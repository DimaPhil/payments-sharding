package com.yandex.payments.sharding.api.converter.payment;

import com.yandex.payments.sharding.api.model.Payment;
import com.yandex.payments.sharding.dto.request.PaymentRequestDto;
import org.modelmapper.AbstractConverter;

public class PaymentRequestDtoToPaymentConverter extends AbstractConverter<PaymentRequestDto, Payment> {
    @Override
    protected Payment convert(PaymentRequestDto paymentRequestDto) {
        return new Payment(
            null,
            paymentRequestDto.getFromId(),
            paymentRequestDto.getToId(),
            paymentRequestDto.getAmount()
        );
    }
}
