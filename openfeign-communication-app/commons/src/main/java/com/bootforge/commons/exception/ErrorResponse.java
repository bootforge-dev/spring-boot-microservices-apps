package com.bootforge.commons.exception;

import lombok.Builder;

import java.util.Map;

@Builder
public record ErrorResponse(
        Integer statusCode,
        String errorStatus,
        String message,
        String path,
        Map<String, String> errors
) {
}
