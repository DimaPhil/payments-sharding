package com.yandex.payments.sharding.dto.factory;

import com.yandex.payments.sharding.dto.request.PaymentRequestDto;
import com.yandex.payments.sharding.dto.response.SummaryExpenseResponseDto;

import java.math.BigDecimal;

public class DtoFactory {
    private DtoFactory() {
    }

    public static PaymentRequestDto paymentRequestDto(Long fromId, Long toId, BigDecimal amount) {
        return new PaymentRequestDto(fromId, toId, amount);
    }

    public static SummaryExpenseResponseDto summaryExpenseResponseDto(Long accountId, BigDecimal amount) {
        return new SummaryExpenseResponseDto(accountId, amount);
    }
}
