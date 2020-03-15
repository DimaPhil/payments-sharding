package com.yandex.payments.sharding.api.configuration.command;

import com.yandex.payments.sharding.api.command.payment.AddPaymentCommand;
import com.yandex.payments.sharding.api.command.payment.BatchAddPaymentCommand;
import com.yandex.payments.sharding.api.command.payment.GetSummaryExpenseCommand;
import com.yandex.payments.sharding.api.service.payment.PaymentService;
import com.yandex.payments.sharding.api.validator.payment.BatchPaymentRequestDtoValidator;
import com.yandex.payments.sharding.api.validator.payment.GetSummaryExpenseValidator;
import com.yandex.payments.sharding.api.validator.payment.PaymentRequestDtoValidator;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandConfiguration {
    @Bean
    public AddPaymentCommand addPaymentCommand(
        PaymentRequestDtoValidator paymentRequestDtoValidator,
        PaymentService paymentService,
        ModelMapper modelMapper
    ) {
        return new AddPaymentCommand(paymentRequestDtoValidator, paymentService, modelMapper);
    }

    @Bean
    public GetSummaryExpenseCommand getSummaryExpenseCommand(
        GetSummaryExpenseValidator getSummaryExpenseValidator,
        PaymentService paymentService
    ) {
        return new GetSummaryExpenseCommand(getSummaryExpenseValidator, paymentService);
    }

    @Bean
    public BatchAddPaymentCommand batchAddPaymentCommand(
        BatchPaymentRequestDtoValidator batchPaymentRequestDtoValidator,
        PaymentService paymentService,
        ModelMapper modelMapper
    ) {
        return new BatchAddPaymentCommand(batchPaymentRequestDtoValidator, paymentService, modelMapper);
    }
}
