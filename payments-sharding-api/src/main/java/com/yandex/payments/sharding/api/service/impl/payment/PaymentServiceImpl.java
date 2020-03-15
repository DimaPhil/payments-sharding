package com.yandex.payments.sharding.api.service.impl.payment;

import com.yandex.payments.sharding.api.model.Payment;
import com.yandex.payments.sharding.api.model.db.PaymentDataSource;
import com.yandex.payments.sharding.api.repository.PaymentRepository;
import com.yandex.payments.sharding.api.service.payment.PaymentService;
import com.yandex.payments.sharding.core.distributor.ShardDistributor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final ShardDistributor<Payment, PaymentDataSource> shardDistributor;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              ShardDistributor<Payment, PaymentDataSource> shardDistributor) {
        this.paymentRepository = paymentRepository;
        this.shardDistributor = shardDistributor;
    }

    @Override
    public void save(Payment payment) {
        PaymentDataSource dataSource = shardDistributor.getShard(payment);
        paymentRepository.save(dataSource, payment);
    }

    @Override
    public void batchSave(List<Payment> payments) {
        Map<Long, List<Payment>> paymentsByPayer = payments
            .stream()
            .collect(Collectors.groupingBy(Payment::getFromId));
        paymentsByPayer.forEach((payerId, value) -> {
            Payment selfPayment = new Payment(0L, payerId, payerId, BigDecimal.ZERO);
            PaymentDataSource dataSource = shardDistributor.getShard(selfPayment);
            paymentRepository.save(dataSource, value);
        });
    }

    @Override
    public BigDecimal getExpenses(Long accountId) {
        Payment selfPayment = new Payment(0L, accountId, accountId, BigDecimal.ZERO); // fake payment to get sharding key
        PaymentDataSource dataSource = shardDistributor.getShard(selfPayment);
        return paymentRepository.getExpenses(dataSource, accountId);
    }
}
