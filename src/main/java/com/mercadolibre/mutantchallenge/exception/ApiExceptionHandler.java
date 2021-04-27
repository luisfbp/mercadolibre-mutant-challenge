package com.mercadolibre.mutantchallenge.exception;

import com.mercadolibre.mutantchallenge.model.api.ApiError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * Intercept all the exceptions in the API
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ApiExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

    /**
     * Default handler Exception
     * @param ex Caught exception.
     * @return Api error response.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> exception (Exception ex) {
        LOGGER.error("Unexpected ERROR", ex);
        return new ResponseEntity<>(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now(), "Unexpected ERROR"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Custom throwable object to let the developer throw custom errors.
     *
     * @param ex Custom throwable.
     * @return Api error response.
     */
    @ExceptionHandler(ApiThrowable.class)
    public ResponseEntity<ApiError> exception (ApiThrowable ex) {

        if (Objects.nonNull(ex)) {
            LOGGER.error("Error threw: {}" , ex.getMessage());
            HttpStatus status = ex.getHttpStatus() == null ? HttpStatus.INTERNAL_SERVER_ERROR : ex.getHttpStatus();
            return new ResponseEntity<>(new ApiError(status, LocalDateTime.now(), ex.getApiMessage()), status);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
