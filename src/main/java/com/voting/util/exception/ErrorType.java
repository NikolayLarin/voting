package com.voting.util.exception;

public enum ErrorType {
    APP_ERROR("Application error"),
    //  http://stackoverflow.com/a/22358422/548473
    DATA_NOT_FOUND("Data not found"),
    DATA_ERROR("Data error"),
    VALIDATION_ERROR("Validation error");

    private final String message;

    ErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
