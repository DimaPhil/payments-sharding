package com.yandex.payments.sharding.core.distributor;

@FunctionalInterface
public interface ShardKey {
    int key();
}
