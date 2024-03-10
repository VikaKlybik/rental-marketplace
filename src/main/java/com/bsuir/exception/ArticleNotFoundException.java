package com.bsuir.exception;

import static com.bsuir.constant.RentalPropertiesConstants.Exception.ARTICLE_NOT_FOUND;

public class ArticleNotFoundException extends RuntimeException {
    public ArticleNotFoundException() {
        super(ARTICLE_NOT_FOUND);
    }
}