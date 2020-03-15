package com.yandex.payments.sharding.api.command.payment;

import com.yandex.payments.sharding.api.command.Command;
import com.yandex.payments.sharding.api.model.Payment;
import com.yandex.payments.sharding.api.service.payment.PaymentService;
import com.yandex.payments.sharding.api.validator.payment.BatchPaymentRequestDtoValidator;
import com.yandex.payments.sharding.dto.request.BatchPaymentRequestDto;
import org.modelmapper.ModelMapper;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.stream.Collectors;

public class BatchAddPaymentCommand implements Command<BatchPaymentRequestDto> {
    private final BatchPaymentRequestDtoValidator batchPaymentRequestDtoValidator;
    private final PaymentService paymentService;
    private final ModelMapper modelMapper;

    public BatchAddPaymentCommand(
        BatchPaymentRequestDtoValidator batchPaymentRequestDtoValidator,
        PaymentService paymentService,
        ModelMapper modelMapper
    ) {
        this.batchPaymentRequestDtoValidator = batchPaymentRequestDtoValidator;
        this.paymentService = paymentService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void process(BatchPaymentRequestDto input) {
        batchPaymentRequestDtoValidator.validate(input);
        List<Payment> payments = input.getPayments()
            .stream()
            .map(payment -> modelMapper.map(payment, Payment.class))
            .collect(Collectors.toList());
        paymentService.batchSave(payments);
    }
}
