package com.bsuir.handler;


import com.bsuir.dto.PropertyResponse;
import com.bsuir.entity.User;
import com.bsuir.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Objects;

@ControllerAdvice
@RequestMapping(method = RequestMethod.GET)
@RequiredArgsConstructor
public class BookmarkHandler {
    private final BookmarkService bookmarkService;

    @ModelAttribute
    public void addBookmarkSize(Model model, @AuthenticationPrincipal User user) {
        if (!Objects.isNull(user)) {
            List<Long> propertyResponseList = bookmarkService.getBookmarkUser(user.getUsername()).stream()
                    .map(PropertyResponse::getId)
                    .toList();

            model.addAttribute("bookmarkPropertiesId", propertyResponseList);
            model.addAttribute("bookmarkSize", propertyResponseList.size());
        }
    }
}