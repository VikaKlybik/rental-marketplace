package com.bsuir.exception;

import lombok.Builder;

@Builder
public record ErrorDetails(
        Integer statusCode,
        String message,
        String description
) {
}