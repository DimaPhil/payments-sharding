package com.yandex.payments.sharding.dto.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yandex.payments.sharding.dto.DtoHelper;
import com.yandex.payments.sharding.dto.factory.DtoFactory;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PaymentRequestDtoTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testSerialization() throws JsonProcessingException {
        PaymentRequestDto paymentRequestDto = DtoFactory.paymentRequestDto(1L, 2L, new BigDecimal("2.39"));
        assertEquals(
            "{\"fromId\":1,\"toId\":2,\"amount\":2.39}",
            objectMapper.writeValueAsString(paymentRequestDto)
        );
    }

    @Test
    public void testDeserialization() throws IOException {
        PaymentRequestDto expectedPaymentRequestDto = DtoFactory.paymentRequestDto(1L, 2L, new BigDecimal("2.39"));
        assertTrue(DtoHelper.equals(
            expectedPaymentRequestDto,
            objectMapper.readValue(
                String.format(
                    "{\"fromId\":%d,\"toId\":%d,\"amount\":%s}",
                    expectedPaymentRequestDto.getFromId(),
                    expectedPaymentRequestDto.getToId(),
                    expectedPaymentRequestDto.getAmount()
                ),
                PaymentRequestDto.class
            )
        ));
    }
}