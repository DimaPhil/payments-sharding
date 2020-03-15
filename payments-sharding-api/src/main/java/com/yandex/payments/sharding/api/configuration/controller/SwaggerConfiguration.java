package com.yandex.payments.sharding.api.configuration.controller;

import com.google.common.base.Predicate;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    private static final List<Predicate<String>> IGNORED_ENDPOINT_PREDICATES = Collections.singletonList(
        PathSelectors.regex("/error.*")
    );

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(endpoint ->
                IGNORED_ENDPOINT_PREDICATES.stream().noneMatch(predicate -> predicate.apply(endpoint))
            )
            .build();
    }
}
