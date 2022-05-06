package com.example.mileage.enums;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    DUPLICATE_ENTRY(HttpStatus.BAD_REQUEST, 4001, "Duplicate entry"),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, 4002, "Invalid input value"),
    SERVER_UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 5001, "Server unknown error"),
    ;

    private final HttpStatus status;
    private final int code;
    private final String message;

    ErrorCode(HttpStatus status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }

}
