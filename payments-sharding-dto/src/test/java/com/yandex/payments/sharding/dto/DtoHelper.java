package com.yandex.payments.sharding.dto;

import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

public class DtoHelper {
    public static <T, R> boolean equals(T obj1, R obj2) {
        return new ReflectionEquals(obj1).matches(obj2);
    }
}
