package com.yandex.payments.sharding.api.repository.impl;

import com.yandex.payments.sharding.api.model.Payment;
import com.yandex.payments.sharding.api.repository.PaymentRepository;
import com.yandex.payments.sharding.api.repository.QueryExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class PaymentRepositoryImpl implements PaymentRepository {
    private static final Logger logger = LoggerFactory.getLogger(PaymentRepositoryImpl.class);

    @Override
    public void save(DataSource dataSource, Payment payment) {
        try {
            QueryExecutor.executeUpdate(
                dataSource,
                String.format(
                    Locale.ENGLISH,
                    "INSERT INTO payment (from_id, to_id, amount) VALUES (%d, %d, %s);",
                    payment.getFromId(), payment.getToId(), payment.getAmount()
                )
            );
        } catch (SQLException e) {
            throw new IllegalStateException("Error when writing payment to the database", e);
        }
    }

    @Override
    public void save(DataSource dataSource, List<Payment> payments) {
        try {
            QueryExecutor.executeUpdate(
                dataSource,
                String.format(
                    Locale.ENGLISH,
                    "INSERT INTO payment (from_id, to_id, amount) VALUES %s;",
                    payments
                        .stream()
                        .map(payment -> String.format(
                            Locale.ENGLISH,
                            "(%d, %d, %s)",
                            payment.getFromId(), payment.getToId(), payment.getAmount()
                        ))
                        .collect(Collectors.joining(","))
                )
            );
        } catch (SQLException e) {
            throw new IllegalStateException("Error when writing payment to the database", e);
        }
    }

    @Override
    public BigDecimal getExpenses(DataSource dataSource, Long accountId) {
        try {
            BigDecimal summaryExpenses = QueryExecutor.executeQuery(
                dataSource,
                String.format(
                    Locale.ENGLISH,
                    "SELECT SUM(amount) as sum FROM payment WHERE from_id = %d",
                    accountId
                ),
                resultSet -> {
                    try {
                        resultSet.next();
                        return resultSet.getBigDecimal("sum");
                    } catch (SQLException e) {
                        logger.error("Couldn't parse query result set", e);
                        return BigDecimal.ZERO;
                    }
                }
            );
            return summaryExpenses == null ? BigDecimal.ZERO : summaryExpenses;
        } catch (SQLException e) {
            throw new IllegalStateException("SQLException when retrieving payments from the database", e);
        }
    }
}
