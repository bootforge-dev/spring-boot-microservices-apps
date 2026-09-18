package com.bootforge.commons.exception;

import java.util.Map;

public class ErrorResponseBuilder {
    public static ErrorResponse errorBuilder(
            Integer statusCode,
            String errorStatus,
            String message,
            String path,
            Map<String, String> errors
    ){
        return ErrorResponse.builder()
                .statusCode(statusCode)
                .errorStatus(errorStatus)
                .message(message)
                .path(path)
                .errors(errors)
                .build();
    }

    public static ErrorResponse errorBuilder(
            Integer statusCode,
            String errorStatus,
            String message,
            String path
    ){
        return ErrorResponse.builder()
                .statusCode(statusCode)
                .errorStatus(errorStatus)
                .message(message)
                .path(path)
                .build();
    }
}
