package com.yandex.payments.sharding.api.exceptions;

import com.yandex.payments.sharding.api.exceptions.validation.UserDataValidationException;
import org.modelmapper.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global exception handler.
 * Handles all exceptions occurred in application runtime, and converts them to HTTP responses.
 *
 * @author f00494583
 * @since 2020-01-22
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    /**
     * Handles application runtime exception and converts them to HTTP BAD_REQUEST response.
     *
     * @param ex Exception which has occurred.
     * @param request HTTP request which produced exception.
     * @return Response entity with HTTP BAD_REQUEST status code and error message inside the body.
     */
    @ExceptionHandler({
            UserDataValidationException.class,
    })
    public ResponseEntity<Object> handleValidationException(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(
                ex,
                ex.getMessage(),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    /**
     * Handles ModelMapper runtime exception and converts them to HTTP BAD_REQUEST response.
     *
     * @param ex Exception which has occurred.
     * @param request HTTP request which produced exception.
     * @return Response entity with HTTP BAD_REQUEST status code and error message inside the body.
     */
    @ExceptionHandler({
            MappingException.class
    })
    public ResponseEntity<Object> handleMappingException(RuntimeException ex, WebRequest request) {
        Throwable cause = ex.getCause();
        return handleExceptionInternal(
                ex,
                "Unable to serialize request. Cause: " + (cause == null ? "no cause" : cause.getMessage()),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    /**
     * Handles the rest exceptions that were not handled by other handlers. Converts to HTTP INTERNAL_SERVER_ERROR.
     *
     * @param ex Exception which has occurred.
     * @param request HTTP request which produced exception.
     * @return Response entity with HTTP INTERNAL_SERVER_ERROR status code and error message inside the body.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        LOGGER.error("Unhandled internal exception: ", ex);
        return handleExceptionInternal(
                ex,
                "Internal server error",
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );
    }
}
