package com.yandex.payments.sharding.dto.response;

import java.math.BigDecimal;

public class SummaryExpenseResponseDto {
    private Long accountId;
    private BigDecimal expenseAmount;

    public SummaryExpenseResponseDto() {
    }

    public SummaryExpenseResponseDto(Long accountId, BigDecimal expenseAmount) {
        this.accountId = accountId;
        this.expenseAmount = expenseAmount;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(BigDecimal expenseAmount) {
        this.expenseAmount = expenseAmount;
    }
}
