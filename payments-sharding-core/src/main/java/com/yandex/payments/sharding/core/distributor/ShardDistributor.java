package com.yandex.payments.sharding.core.distributor;

/**
 * Distributes the given input objects between shards.
 *
 * @param <T> The type of input object
 * @param <S> The type of output shard
 */
public interface ShardDistributor<T extends ShardKey, S> {
    /**
     * Returns the shard for the input object.
     *
     * @param obj Input object
     * @return Shard corresponding to the provided object
     */
    S getShard(T obj);

    /**
     * Returns the number of shards in the system.
     *
     * @return The number of shards in the system.
     */
    int getShardsCount();
}
