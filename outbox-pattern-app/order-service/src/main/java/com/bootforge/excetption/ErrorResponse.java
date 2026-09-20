package com.bootforge.excetption;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
public record ErrorResponse(
        LocalDateTime timestamp,
        String message,
        Integer statusCode,
        String path,
        Map<String, String> errors
) {
    public static ErrorResponse build(LocalDateTime timestamp,
                                      String message,
                                      Integer statusCode,
                                      String path,
                                      Map<String, String> errors) {
        return ErrorResponse.builder()
                .timestamp(timestamp)
                .message(message)
                .statusCode(statusCode)
                .path(path)
                .errors(errors)
                .build();
    }
    public static ErrorResponse build(LocalDateTime timestamp,
                                      String message,
                                      Integer statusCode,
                                      String path) {
        return ErrorResponse.builder()
                .timestamp(timestamp)
                .message(message)
                .statusCode(statusCode)
                .path(path)
                .build();
    }
}
