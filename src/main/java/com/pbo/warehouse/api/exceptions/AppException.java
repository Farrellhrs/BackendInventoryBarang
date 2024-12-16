package com.pbo.warehouse.api.exceptions;

public class AppException extends RuntimeException {
    private final int statusCode;
    private final String message;

    public AppException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }

    public AppException(int statusCode, String message, String developerMessage) {
        super(developerMessage);
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
