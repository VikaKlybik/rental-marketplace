package com.bsuir.exception;

import static com.bsuir.constant.RentalPropertiesConstants.Exception.CHAT_NOT_FOUND;

public class ChatNotFoundException extends RuntimeException {
    public ChatNotFoundException() {
        super(CHAT_NOT_FOUND);
    }
}