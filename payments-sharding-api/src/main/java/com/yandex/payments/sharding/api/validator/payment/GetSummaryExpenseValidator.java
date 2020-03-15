package com.yandex.payments.sharding.api.validator.payment;

import com.yandex.payments.sharding.api.validator.Validator;

public class GetSummaryExpenseValidator extends Validator<Long> {
    public GetSummaryExpenseValidator() {
        addValidation(
            accountId -> accountId != null && accountId > 0,
            "Expense account id should be present and be greater zero"
        );
    }
}
