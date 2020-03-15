package com.yandex.payments.sharding.core.distributor.impl;

import com.yandex.payments.sharding.core.distributor.ShardKey;

public class ShardingObject implements ShardKey {
    private final int value;

    public ShardingObject(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public int key() {
        return value;
    }
}
