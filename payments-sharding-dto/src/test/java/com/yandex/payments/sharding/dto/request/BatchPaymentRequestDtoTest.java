package com.yandex.payments.sharding.dto.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yandex.payments.sharding.dto.DtoHelper;
import com.yandex.payments.sharding.dto.factory.DtoFactory;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BatchPaymentRequestDtoTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testSerialization() throws JsonProcessingException {
        BatchPaymentRequestDto batchPaymentRequestDto = new BatchPaymentRequestDto(Arrays.asList(
            DtoFactory.paymentRequestDto(1L, 2L, new BigDecimal("2.39")),
            DtoFactory.paymentRequestDto(10L, 239L, new BigDecimal("2000.12"))
        ));
        assertEquals(
            "{\"payments\":[{\"fromId\":1,\"toId\":2,\"amount\":2.39},{\"fromId\":10,\"toId\":239,\"amount\":2000.12}]}",
            objectMapper.writeValueAsString(batchPaymentRequestDto)
        );
    }

    @Test
    public void testDeserialization() throws IOException {
        BatchPaymentRequestDto expectedBatchPaymentRequestDto = new BatchPaymentRequestDto(Arrays.asList(
            DtoFactory.paymentRequestDto(1L, 2L, new BigDecimal("2.39")),
            DtoFactory.paymentRequestDto(10L, 239L, new BigDecimal("20.12"))
        ));
        BatchPaymentRequestDto actual = objectMapper.readValue(
            String.format(
                "{\"payments\":[{\"fromId\":%d,\"toId\":%d,\"amount\":%s},{\"fromId\":%d,\"toId\":%d,\"amount\":%s}]}",
                expectedBatchPaymentRequestDto.getPayments().get(0).getFromId(),
                expectedBatchPaymentRequestDto.getPayments().get(0).getToId(),
                expectedBatchPaymentRequestDto.getPayments().get(0).getAmount(),
                expectedBatchPaymentRequestDto.getPayments().get(1).getFromId(),
                expectedBatchPaymentRequestDto.getPayments().get(1).getToId(),
                expectedBatchPaymentRequestDto.getPayments().get(1).getAmount()
            ),
            BatchPaymentRequestDto.class
        );
        assertEquals(expectedBatchPaymentRequestDto.getPayments().size(), actual.getPayments().size());
        assertEquals(expectedBatchPaymentRequestDto.getPayments().get(0).getFromId(), actual.getPayments().get(0).getFromId());
        assertEquals(expectedBatchPaymentRequestDto.getPayments().get(0).getToId(), actual.getPayments().get(0).getToId());
        assertEquals(0, expectedBatchPaymentRequestDto.getPayments().get(0).getAmount().compareTo(actual.getPayments().get(0).getAmount()));
        assertEquals(expectedBatchPaymentRequestDto.getPayments().get(1).getFromId(), actual.getPayments().get(1).getFromId());
        assertEquals(expectedBatchPaymentRequestDto.getPayments().get(1).getToId(), actual.getPayments().get(1).getToId());
        assertEquals(0, expectedBatchPaymentRequestDto.getPayments().get(1).getAmount().compareTo(actual.getPayments().get(1).getAmount()));
    }
}