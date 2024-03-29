package com.bsuir.handler;

import com.bsuir.exception.ArticleNotFoundException;
import com.bsuir.exception.BookmarkNotFoundException;
import com.bsuir.exception.ChatNotFoundException;
import com.bsuir.exception.DuplicateException;
import com.bsuir.exception.ErrorDetails;
import com.bsuir.exception.PropertyAlreadyClosedFoundException;
import com.bsuir.exception.PropertyCreateNotAllowException;
import com.bsuir.exception.PropertyNotFoundException;
import com.bsuir.exception.RoleNotFoundException;
import com.bsuir.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ArticleNotFoundException.class, BookmarkNotFoundException.class, ChatNotFoundException.class, PropertyNotFoundException.class, UserNotFoundException.class, RoleNotFoundException.class})
    public String handleNoSuchCarException(Exception exception, Model model) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(),
                String.format("404 - %s", exception.getMessage()),
                "Извините, мы не смогли найти что-то по вашему запросу :(");
        model.addAttribute("errorDetails", errorDetails);
        return "error";
    }

    @ExceptionHandler({PropertyAlreadyClosedFoundException.class})
    public String handleBookingNotFoundException(Exception exception, Model model) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND.value(),
                String.format("500 - %s", exception.getMessage()),
                "Извините, мы не смогли обработать ваш запрос корректно :(");
        model.addAttribute("errorDetails", errorDetails);
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleAnyOtherException(Exception exception, Model model) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "500 - Ошибка сервера",
                "Извините, мы не смогли обработать ваш запрос корректно :(");
        model.addAttribute("errorDetails", errorDetails);
        return "error";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleForbiddenException(AccessDeniedException e, Model model) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.FORBIDDEN.value(),
                "Ошибка доступа",
                "Извините, вам запрещён доступ на данную страницу :(");
        model.addAttribute("errorDetails", errorDetails);
        return "error";
    }

    @ExceptionHandler({DuplicateException.class, PropertyCreateNotAllowException.class})
    @ResponseBody
    public ErrorDetails handleDuplicateException(Exception exception) {
        return ErrorDetails.builder()
                .description(exception.getMessage())
                .statusCode(HttpStatus.CONTINUE.value())
                .message(exception.getMessage())
                .build();
    }

}