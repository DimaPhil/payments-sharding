package com.yandex.payments.sharding.api.command;

/**
 * Interface for command layer. Receives input, but doesn't return any value.
 *
 * @param <T> The type of the command input.
 */
public interface Command<T> {
    /**
     * Processes the given input (and returns nothing).
     *
     * @param input The given input.
     */
    void process(T input);
}