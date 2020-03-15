package com.yandex.payments.sharding.core.distributor.impl;

class WeightedShard<S> {
    private final S shard;
    private final int key;

    public WeightedShard(S shard, int key) {
        this.shard = shard;
        this.key = key;
    }

    public S getShard() {
        return shard;
    }

    public int getKey() {
        return key;
    }
}
