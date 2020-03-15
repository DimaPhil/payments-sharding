package com.yandex.payments.sharding.api.exceptions.validation;

public class UserDataValidationException extends RuntimeException {
    public UserDataValidationException(String message) {
        super(message);
    }
}
