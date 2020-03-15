package com.yandex.payments.sharding.api.validator;

import com.yandex.payments.sharding.api.exceptions.validation.UserDataValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Abstract class for validating request DTOs on API side.
 *
 * @param <T> The type of the input value to validate.
 */
public abstract class Validator<T> {
    private List<Validation<T>> validations;

    protected Validator() {
        this.validations = new ArrayList<>();
    }

    protected void addValidation(Function<T, Boolean> validation, String errorMessage) {
        validations.add(new Validation<>(validation, errorMessage));
    }

    public void validate(T input) {
        validations.forEach(validation -> validation.validate(input));
    }

    private static final class Validation<T> {
        private final Function<T, Boolean> validation;
        private final String errorMessage;

        Validation(Function<T, Boolean> validation, String errorMessage) {
            this.validation = validation;
            this.errorMessage = errorMessage;
        }

        void validate(T input) {
            if (!validation.apply(input)) {
                throw new UserDataValidationException(errorMessage);
            }
        }
    }
}
