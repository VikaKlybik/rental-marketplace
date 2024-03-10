package com.bsuir.exception;

import static com.bsuir.constant.RentalPropertiesConstants.Exception.ROLE_NOT_FOUND;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException() {
        super(ROLE_NOT_FOUND);
    }
}