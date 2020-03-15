package com.yandex.payments.sharding.api.configuration.mapper;

import com.yandex.payments.sharding.api.converter.payment.PaymentRequestDtoToPaymentConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addConverter(new PaymentRequestDtoToPaymentConverter());
        return modelMapper;
    }
}
