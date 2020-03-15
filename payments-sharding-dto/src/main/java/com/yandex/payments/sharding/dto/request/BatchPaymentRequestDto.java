package com.yandex.payments.sharding.dto.request;

import java.util.List;

public class BatchPaymentRequestDto {
    private List<PaymentRequestDto> payments;

    public BatchPaymentRequestDto() {
    }

    public BatchPaymentRequestDto(List<PaymentRequestDto> payments) {
        this.payments = payments;
    }

    public void setPayments(List<PaymentRequestDto> payments) {
        this.payments = payments;
    }

    public List<PaymentRequestDto> getPayments() {
        return payments;
    }
}
