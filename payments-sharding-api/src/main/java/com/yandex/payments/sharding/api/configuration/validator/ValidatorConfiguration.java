package com.yandex.payments.sharding.api.configuration.validator;

import com.yandex.payments.sharding.api.validator.payment.BatchPaymentRequestDtoValidator;
import com.yandex.payments.sharding.api.validator.payment.GetSummaryExpenseValidator;
import com.yandex.payments.sharding.api.validator.payment.PaymentRequestDtoValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorConfiguration {
    @Bean
    public PaymentRequestDtoValidator paymentRequestDtoValidator() {
        return new PaymentRequestDtoValidator();
    }

    @Bean
    public GetSummaryExpenseValidator getSummaryExpenseValidator() {
        return new GetSummaryExpenseValidator();
    }

    @Bean
    public BatchPaymentRequestDtoValidator batchPaymentRequestDtoValidator(PaymentRequestDtoValidator paymentRequestDtoValidator) {
        return new BatchPaymentRequestDtoValidator(paymentRequestDtoValidator);
    }
}
