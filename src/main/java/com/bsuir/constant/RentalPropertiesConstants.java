package com.bsuir.constant;

import java.math.BigDecimal;

public interface RentalPropertiesConstants {
    interface DefaultValue {
        int PAGE = 0;
        int ELEMENT_PER_PAGE = 6;
        String SORT_ASC = "asc";
        String SORT_DESC = "dsc";
        String DEFAULT_ICON_URL = "1ZsU0bkckYSifEkRzwd0nbda8V4JDmkyE";
        BigDecimal PRICE_FOR_PUBLISH = BigDecimal.valueOf(5.00f);
        String NEW_ARTICLE_SUBJECT = "Внимание! Не упусти новую статью!";
        String ARTICLE_TEMPLATE_NAME = "article-create-email";

    }
    interface Exception {
        String PROPERTY_NOT_FOUND = "Объявление не найдено!";
        String PROPERTY_ALREADY_CLOSED = "Объявление с id = '%d' уже закрыто";
        String USER_WITH_ID_NOT_FOUND = "Пользователь с id = '%d' не найден";
        String USER_WITH_USERNAME_NOT_FOUND = "Пользователь с именем = '%s' не найден";
        String ROLE_NOT_FOUND = "Данная роль не найдена";
        String IMAGE_NOT_FOUND = "Картинка не найдена";
        String CHAT_NOT_FOUND = "Чат не найден";
        String ARTICLE_NOT_FOUND = "Данная статья не найдена";
        String PROPERTY_NOT_ALLOW = "Пользователь '%s' не оплатил возможности создание дополнительных объявлений";
        String BOOKMARK_NOT_FOUND = "Ошибка! У пользователя нет сохранённых объявлений!";
    }
    interface Validation {
        interface ErrorMessage {
            String FIELD_EMPTY = "Заполните пожалуйста поле!";
            String NOT_VALID_FORMAT = "Неправильный формат данных!";
        }
    }
    String GOOGLE_IMG_LINK = "https://drive.google.com/uc?export=view&id=";
}