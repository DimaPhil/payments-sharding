package com.yandex.payments.sharding.api.command.payment;

import com.yandex.payments.sharding.api.command.Command;
import com.yandex.payments.sharding.api.model.Payment;
import com.yandex.payments.sharding.api.service.payment.PaymentService;
import com.yandex.payments.sharding.api.validator.payment.PaymentRequestDtoValidator;
import com.yandex.payments.sharding.dto.request.PaymentRequestDto;
import org.modelmapper.ModelMapper;

public class AddPaymentCommand implements Command<PaymentRequestDto> {
    private final PaymentRequestDtoValidator paymentRequestDtoValidator;
    private final PaymentService paymentService;
    private final ModelMapper modelMapper;

    public AddPaymentCommand(
        PaymentRequestDtoValidator paymentRequestDtoValidator,
        PaymentService paymentService,
        ModelMapper modelMapper
    ) {
        this.paymentRequestDtoValidator = paymentRequestDtoValidator;
        this.paymentService = paymentService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void process(PaymentRequestDto input) {
        paymentRequestDtoValidator.validate(input);
        paymentService.save(modelMapper.map(input, Payment.class));
    }
}
