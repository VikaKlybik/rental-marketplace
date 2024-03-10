package com.bsuir.exception;

public record ErrorDetails(
        Integer statusCode,
        String message,
        String description
) {
}