package com.bsuir.exception;

import static com.bsuir.constant.RentalPropertiesConstants.Exception.USER_WITH_ID_NOT_FOUND;
import static com.bsuir.constant.RentalPropertiesConstants.Exception.USER_WITH_USERNAME_NOT_FOUND;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super(String.format(USER_WITH_ID_NOT_FOUND, id));
    }

    public UserNotFoundException(String username) {
        super(String.format(USER_WITH_USERNAME_NOT_FOUND, username));
    }
}