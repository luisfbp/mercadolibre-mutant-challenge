package com.mercadolibre.mutantchallenge.exception;

import com.mercadolibre.mutantchallenge.model.api.ApiError;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;

/**
 * Let throw custom errors
 */
public class ApiThrowable extends NestedRuntimeException {

    private String message;
    private String apiMessage;
    private Throwable error;
    private HttpStatus httpStatus;

    public ApiThrowable(String apiMessage, String message, Throwable error) {
        super(message, error);
        this.message = message;
        this.error = error;
        this.apiMessage = apiMessage;
    }

    public ApiThrowable(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.apiMessage = message;
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getApiMessage() {
        return apiMessage;
    }

    public void setApiMessage(String apiMessage) {
        this.apiMessage = apiMessage;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}