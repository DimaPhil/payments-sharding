package com.yandex.payments.sharding.api.model;

import com.yandex.payments.sharding.core.distributor.ShardKey;

import java.math.BigDecimal;

public class Payment implements ShardKey {
    private final Long paymentId;
    private final Long fromId;
    private final Long toId;
    private final BigDecimal amount;

    public Payment(Long paymentId, Long fromId, Long toId, BigDecimal amount) {
        this.paymentId = paymentId;
        this.fromId = fromId;
        this.toId = toId;
        this.amount = amount;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public Long getFromId() {
        return fromId;
    }

    public Long getToId() {
        return toId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public int key() {
        // Because our API needs to get all payments for a single account (payer),
        // we want all of them to be stored on the same shard
        return Long.hashCode(fromId);
    }
}
