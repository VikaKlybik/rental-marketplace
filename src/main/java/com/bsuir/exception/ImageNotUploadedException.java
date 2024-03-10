package com.bsuir.exception;

public class ImageNotUploadedException extends RuntimeException {
    public ImageNotUploadedException(String message) {
        super(message);
    }
}