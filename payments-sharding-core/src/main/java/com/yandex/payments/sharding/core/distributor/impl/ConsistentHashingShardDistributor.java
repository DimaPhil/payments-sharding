package com.yandex.payments.sharding.core.distributor.impl;

import com.yandex.payments.sharding.core.distributor.ShardDistributor;
import com.yandex.payments.sharding.core.distributor.ShardKey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of sharding based on consistent hashing algorithm.
 *
 * Servers (shards) are being placed on a circle based on their hash codes.
 * Each shard is duplicated several times based on its weight, which are provided at construction time.
 *
 * On request, when object needs to be sharded on one of the servers (shards), sharding key is calculated for it and
 * the needed shard is found using binary search algorithm by shard's keys.
 *
 * @param <T> The type of the input parameters, should have sharding key
 * @param <S> The type of the server parameters (shards)
 */
public class ConsistentHashingShardDistributor<T extends ShardKey, S> implements ShardDistributor<T, S> {
    private static final int DEFAULT_SHARD_WEIGHT = 10;

    private final List<WeightedShard<S>> weightedShards;

    public ConsistentHashingShardDistributor(List<S> shards) {
        this(shards, Collections.nCopies(shards.size(), DEFAULT_SHARD_WEIGHT));
    }

    public ConsistentHashingShardDistributor(List<S> shards, List<Integer> weights) {
        Objects.requireNonNull(shards, "Input shards cannot be null");
        Objects.requireNonNull(weights, "Input weights cannot be null");
        if (shards.size() != weights.size()) {
            throw new IllegalArgumentException("Shards list and weights list should have the same size");
        }
        if (shards.size() == 0) {
            throw new IllegalArgumentException("At least one shard should be provided");
        }
        this.weightedShards = new ArrayList<>(shards.size());
        for (int shardIndex = 0; shardIndex < shards.size(); shardIndex++) {
            S shard = shards.get(shardIndex);
            int shardHashCode = shard.hashCode();
            int weight = weights.get(shardIndex);
            if (weight < 0) {
               throw new IllegalArgumentException("Input shard weight should be non-negative");
            }
            for (int iteration = 1; iteration <= weight; iteration++) {
                weightedShards.add(new WeightedShard<>(shard, shardHashCode * iteration));
            }
        }
        weightedShards.sort(Comparator.comparing(WeightedShard::getKey));
    }

    @Override
    public S getShard(T obj) {
        int shardKey = obj.key();
        int shardIndex = nextShardIndex(shardKey);
        return weightedShards.get(shardIndex).getShard();
    }

    @Override
    public int getShardsCount() {
        return weightedShards.size();
    }

    private int nextShardIndex(int key) {
        int l = -1;
        int r = weightedShards.size();
        while (l < r - 1) {
            int m = (l + r) / 2;
            int shardKey = weightedShards.get(m).getKey();

            if (key > shardKey) {
                l = m;
            } else {
                r = m;
            }
        }
        return r == weightedShards.size() ? 0 : r;
    }
}
