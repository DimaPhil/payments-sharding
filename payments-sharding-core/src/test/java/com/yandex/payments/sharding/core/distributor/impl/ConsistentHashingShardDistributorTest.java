package com.yandex.payments.sharding.core.distributor.impl;

import com.yandex.payments.sharding.core.distributor.ShardDistributor;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static junit.framework.TestCase.assertEquals;

public class ConsistentHashingShardDistributorTest {
    @Test(expected = NullPointerException.class)
    public void testFailOnNullShardsInput() {
        new ConsistentHashingShardDistributor<ShardingObject, Shard>(null);
    }

    @Test(expected = NullPointerException.class)
    public void testFailOnNullWeightsInput() {
        new ConsistentHashingShardDistributor<ShardingObject, Shard>(
            Collections.singletonList(new Shard("10.0.0.1", 8080)),
            null
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailOnEmptyShardsInput() {
        new ConsistentHashingShardDistributor<ShardingObject, Shard>(
            Collections.emptyList(),
            Collections.emptyList()
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailOnDifferentSizesLists() {
        new ConsistentHashingShardDistributor<ShardingObject, Shard>(
            Collections.singletonList(new Shard("10.0.0.1", 8080)),
            Arrays.asList(1, 2)
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFailOnNegativeWeights() {
        new ConsistentHashingShardDistributor<ShardingObject, Shard>(
            Arrays.asList(
                new Shard("10.0.0.1", 8080),
                new Shard("10.0.0.2", 8081),
                new Shard("10.0.0.3", 8082)
            ),
            Arrays.asList(1, 0, -1)
        );
    }

    @Test
    public void testReturnsShardCorrectly_whenSingleWeights() {
        Shard shard1 = new Shard10();
        Shard shard2 = new Shard20();
        Shard shard3 = new Shard30();
        ShardDistributor<ShardingObject, Shard> shardDistributor = new ConsistentHashingShardDistributor<>(
            Arrays.asList(shard1, shard2, shard3),
            Arrays.asList(1, 1, 1)
        );
        assertEquals(shard1, shardDistributor.getShard(new ShardingObject(0)));
        assertEquals(shard1, shardDistributor.getShard(new ShardingObject(5)));
        assertEquals(shard1, shardDistributor.getShard(new ShardingObject(10)));
        assertEquals(shard2, shardDistributor.getShard(new ShardingObject(15)));
        assertEquals(shard2, shardDistributor.getShard(new ShardingObject(20)));
        assertEquals(shard3, shardDistributor.getShard(new ShardingObject(25)));
        assertEquals(shard3, shardDistributor.getShard(new ShardingObject(30)));
        assertEquals(shard1, shardDistributor.getShard(new ShardingObject(31)));
    }

    @Test
    public void testReturnsShardCorrectly_whenMultiWeights() {
        Shard shard1 = new Shard15();
        Shard shard2 = new Shard21();
        Shard shard3 = new Shard32();
        ShardDistributor<ShardingObject, Shard> shardDistributor = new ConsistentHashingShardDistributor<>(
            Arrays.asList(shard1, shard2, shard3),
            Arrays.asList(3, 2, 1)
        );
        // we have shards on (15, 30, 45) - 1, (21, 42) - 2, 32 - 3
        assertEquals(shard1, shardDistributor.getShard(new ShardingObject(14)));
        assertEquals(shard2, shardDistributor.getShard(new ShardingObject(20)));
        assertEquals(shard2, shardDistributor.getShard(new ShardingObject(21)));
        assertEquals(shard1, shardDistributor.getShard(new ShardingObject(22)));
        assertEquals(shard1, shardDistributor.getShard(new ShardingObject(30)));
        assertEquals(shard3, shardDistributor.getShard(new ShardingObject(31)));
        assertEquals(shard2, shardDistributor.getShard(new ShardingObject(33)));
        assertEquals(shard2, shardDistributor.getShard(new ShardingObject(42)));
        assertEquals(shard1, shardDistributor.getShard(new ShardingObject(43)));
        assertEquals(shard1, shardDistributor.getShard(new ShardingObject(46)));
    }

    // Not a perfect design, but don't have much time on refactoring this
    private static class Shard10 extends Shard {
        @Override
        public int hashCode() {
            return 10;
        }
    }

    private static class Shard20 extends Shard {
        @Override
        public int hashCode() {
            return 20;
        }
    }

    private static class Shard30 extends Shard {
        @Override
        public int hashCode() {
            return 30;
        }
    }

    private static class Shard15 extends Shard {
        @Override
        public int hashCode() {
            return 15;
        }
    }

    private static class Shard21 extends Shard {
        @Override
        public int hashCode() {
            return 21;
        }
    }

    private static class Shard32 extends Shard {
        @Override
        public int hashCode() {
            return 32;
        }
    }
}