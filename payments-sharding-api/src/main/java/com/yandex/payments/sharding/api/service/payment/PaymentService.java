package com.yandex.payments.sharding.api.service.payment;

import com.yandex.payments.sharding.api.model.Payment;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {
    void save(Payment payment);

    void batchSave(List<Payment> payments);

    BigDecimal getExpenses(Long accountId);
}
