package com.bsuir.exception;

import static com.bsuir.constant.RentalPropertiesConstants.Exception.BOOKMARK_NOT_FOUND;

public class BookmarkNotFoundException extends RuntimeException {
    public BookmarkNotFoundException() {
        super(BOOKMARK_NOT_FOUND);
    }
}