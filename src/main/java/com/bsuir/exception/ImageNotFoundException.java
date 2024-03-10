package com.bsuir.exception;

import static com.bsuir.constant.RentalPropertiesConstants.Exception.IMAGE_NOT_FOUND;

public class ImageNotFoundException extends RuntimeException {
    public ImageNotFoundException() {
        super(IMAGE_NOT_FOUND);
    }
}