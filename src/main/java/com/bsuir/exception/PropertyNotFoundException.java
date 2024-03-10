package com.bsuir.exception;

import static com.bsuir.constant.RentalPropertiesConstants.Exception.PROPERTY_NOT_FOUND;

public class PropertyNotFoundException extends RuntimeException {
    public PropertyNotFoundException() {
        super(PROPERTY_NOT_FOUND);
    }
}