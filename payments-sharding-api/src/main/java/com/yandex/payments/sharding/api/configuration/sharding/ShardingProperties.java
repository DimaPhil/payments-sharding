package com.yandex.payments.sharding.api.configuration.sharding;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "payments")
@Component
public class ShardingProperties {
    private List<PaymentShard> shards = new ArrayList<>();

    public ShardingProperties() {
    }

    public List<PaymentShard> getShards() {
        return shards;
    }

    public void setShards(List<PaymentShard> shards) {
        this.shards = shards;
    }
}
