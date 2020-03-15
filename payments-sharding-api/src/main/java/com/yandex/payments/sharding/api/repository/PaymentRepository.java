package com.yandex.payments.sharding.api.repository;

import com.yandex.payments.sharding.api.model.Payment;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

public interface PaymentRepository {
    void save(DataSource dataSource, Payment payment);

    void save(DataSource dataSource, List<Payment> payments);

    BigDecimal getExpenses(DataSource dataSource, Long accountId);
}
