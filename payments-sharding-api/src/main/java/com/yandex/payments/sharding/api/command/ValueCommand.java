package com.yandex.payments.sharding.api.command;

/**
 * Interface for command layer. Receives input and returns result.
 *
 * @param <T> Type parameter of DTO from API layer
 * @param <R> Type parameter of processing result
 */
public interface ValueCommand<T, R> {
    /**
     * Processes the give input to the service layer and returns result from service.
     *
     * @param input DTO from API layer
     * @return Result of processing result on the service layer
     */
    R process(T input);
}
