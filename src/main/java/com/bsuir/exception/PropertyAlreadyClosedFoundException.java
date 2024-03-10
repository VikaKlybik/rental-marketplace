package com.bsuir.exception;

import static com.bsuir.constant.RentalPropertiesConstants.Exception.PROPERTY_ALREADY_CLOSED;

public class PropertyAlreadyClosedFoundException extends RuntimeException {
    public PropertyAlreadyClosedFoundException(Long id) {
        super(String.format(PROPERTY_ALREADY_CLOSED, id));
    }
}