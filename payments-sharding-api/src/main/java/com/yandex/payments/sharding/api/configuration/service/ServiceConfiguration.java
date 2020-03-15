package com.yandex.payments.sharding.api.configuration.service;

import com.yandex.payments.sharding.api.configuration.sharding.PaymentShard;
import com.yandex.payments.sharding.api.configuration.sharding.ShardingProperties;
import com.yandex.payments.sharding.api.model.Payment;
import com.yandex.payments.sharding.api.model.db.PaymentDataSource;
import com.yandex.payments.sharding.api.repository.PaymentRepository;
import com.yandex.payments.sharding.api.service.impl.payment.PaymentServiceImpl;
import com.yandex.payments.sharding.api.service.payment.PaymentService;
import com.yandex.payments.sharding.core.distributor.ShardDistributor;
import com.yandex.payments.sharding.core.distributor.impl.ConsistentHashingShardDistributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ServiceConfiguration {
    @Bean
    public ShardDistributor<Payment, PaymentDataSource> paymentShardDistributor(
        ShardingProperties shardingProperties,
        List<PaymentDataSource> dataSources
    ) {
        return new ConsistentHashingShardDistributor<>(
            dataSources,
            shardingProperties.getShards().stream().map(PaymentShard::getWeight).collect(Collectors.toList())
        );
    }

    @Bean
    public PaymentService paymentService(
        PaymentRepository paymentRepository,
        ShardDistributor<Payment, PaymentDataSource> paymentShardDistributor
    ) {
        return new PaymentServiceImpl(paymentRepository, paymentShardDistributor);
    }
}
