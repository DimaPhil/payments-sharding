package com.yandex.payments.sharding.dto.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yandex.payments.sharding.dto.DtoHelper;
import com.yandex.payments.sharding.dto.factory.DtoFactory;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SummaryExpenseReponseDtoTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testSerialization() throws JsonProcessingException {
        SummaryExpenseResponseDto summaryExpenseResponseDto = DtoFactory.summaryExpenseResponseDto(1L, new BigDecimal("2.39"));
        assertEquals(
            "{\"accountId\":1,\"expenseAmount\":2.39}",
            objectMapper.writeValueAsString(summaryExpenseResponseDto)
        );
    }

    @Test
    public void testDeserialization() throws IOException {
        SummaryExpenseResponseDto expectedSummaryExpenseResponseDto = DtoFactory.summaryExpenseResponseDto(1L, new BigDecimal("2.39"));
        assertTrue(DtoHelper.equals(
            expectedSummaryExpenseResponseDto,
            objectMapper.readValue(
                String.format(
                    "{\"accountId\":%d,\"expenseAmount\":%s}",
                    expectedSummaryExpenseResponseDto.getAccountId(),
                    expectedSummaryExpenseResponseDto.getExpenseAmount()
                ),
                SummaryExpenseResponseDto.class
            )
        ));
    }
}
