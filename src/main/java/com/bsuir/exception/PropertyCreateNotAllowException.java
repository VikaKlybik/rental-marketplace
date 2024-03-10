package com.bsuir.exception;

import static com.bsuir.constant.RentalPropertiesConstants.Exception.PROPERTY_NOT_ALLOW;

public class PropertyCreateNotAllowException extends RuntimeException {
    public PropertyCreateNotAllowException(String username) {
        super(String.format(PROPERTY_NOT_ALLOW, username));
    }
}