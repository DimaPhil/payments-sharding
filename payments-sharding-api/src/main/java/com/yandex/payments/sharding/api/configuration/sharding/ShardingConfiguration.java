package com.yandex.payments.sharding.api.configuration.sharding;

import com.yandex.payments.sharding.api.model.db.PaymentDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ShardingConfiguration {
    @Bean
    public List<PaymentDataSource> paymentDataSources(ShardingProperties shardingProperties) {
        return shardingProperties.getShards()
            .stream()
            .map(shard -> new PaymentDataSource(
                shard.getUrl(),
                shard.getUsername(),
                shard.getPassword(),
                shard.getDriverClassName()
            ))
            .collect(Collectors.toList());
    }
}
