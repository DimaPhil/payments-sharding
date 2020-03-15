package com.yandex.payments.sharding.api.command.payment;

import com.yandex.payments.sharding.api.command.ValueCommand;
import com.yandex.payments.sharding.api.service.payment.PaymentService;
import com.yandex.payments.sharding.api.validator.payment.GetSummaryExpenseValidator;
import com.yandex.payments.sharding.dto.response.SummaryExpenseResponseDto;

public class GetSummaryExpenseCommand implements ValueCommand<Long, SummaryExpenseResponseDto> {
    private final GetSummaryExpenseValidator getSummaryExpenseValidator;
    private final PaymentService paymentService;

    public GetSummaryExpenseCommand(GetSummaryExpenseValidator getSummaryExpenseValidator, PaymentService paymentService) {
        this.getSummaryExpenseValidator = getSummaryExpenseValidator;
        this.paymentService = paymentService;
    }

    @Override
    public SummaryExpenseResponseDto process(Long accountId) {
        getSummaryExpenseValidator.validate(accountId);
        return new SummaryExpenseResponseDto(
            accountId,
            paymentService.getExpenses(accountId)
        );
    }
}
