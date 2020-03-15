package com.yandex.payments.sharding.api.configuration.repository;

import com.yandex.payments.sharding.api.repository.PaymentRepository;
import com.yandex.payments.sharding.api.repository.impl.PaymentRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfiguration {
    @Bean
    public PaymentRepository paymentRepository() {
        return new PaymentRepositoryImpl();
    }
}
