package com.yandex.payments.sharding.api.controller;

import com.yandex.payments.sharding.api.command.payment.AddPaymentCommand;
import com.yandex.payments.sharding.api.command.payment.BatchAddPaymentCommand;
import com.yandex.payments.sharding.api.command.payment.GetSummaryExpenseCommand;
import com.yandex.payments.sharding.dto.request.BatchPaymentRequestDto;
import com.yandex.payments.sharding.dto.request.PaymentRequestDto;
import com.yandex.payments.sharding.dto.response.SummaryExpenseResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndpointValues.PAYMENTS_ENDPOINT)
public class PaymentsController {
    private final AddPaymentCommand addPaymentCommand;
    private final GetSummaryExpenseCommand getSummaryExpenseCommand;
    private final BatchAddPaymentCommand batchAddPaymentCommand;

    public PaymentsController(
        AddPaymentCommand addPaymentCommand,
        GetSummaryExpenseCommand getSummaryExpenseCommand,
        BatchAddPaymentCommand batchAddPaymentCommand) {
        this.addPaymentCommand = addPaymentCommand;
        this.getSummaryExpenseCommand = getSummaryExpenseCommand;
        this.batchAddPaymentCommand = batchAddPaymentCommand;
    }

    @PostMapping
    public ResponseEntity<Object> addPayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        addPaymentCommand.process(paymentRequestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/batch")
    public ResponseEntity<Object> addPaymentsBatch(@RequestBody BatchPaymentRequestDto batchPaymentRequestDto) {
        batchAddPaymentCommand.process(batchPaymentRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SummaryExpenseResponseDto> summaryExpense(@PathVariable("id") Long accountId) {
        SummaryExpenseResponseDto expenseResponse = getSummaryExpenseCommand.process(accountId);
        return ResponseEntity.ok(expenseResponse);
    }
}
