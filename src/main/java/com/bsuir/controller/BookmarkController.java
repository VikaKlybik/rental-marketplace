package com.bsuir.controller;

import com.bsuir.dto.PropertyResponse;
import com.bsuir.entity.User;
import com.bsuir.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/bookmark")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @GetMapping
    public String getUserBookmark(@AuthenticationPrincipal User user, Model model) {
        List<PropertyResponse> bookmarkResponse = bookmarkService.getBookmarkUser(user.getUsername());
        model.addAttribute("bookmarkProperty", bookmarkResponse);
        return "bookmark-overview";
    }

    @PostMapping("/create/{propertyId}")
    public String addToBookMark(@PathVariable Long propertyId, @AuthenticationPrincipal User user) {
        bookmarkService.addBookmarkProperty(user.getUsername(), propertyId);
        return "redirect:/property/"+propertyId;
    }

    @GetMapping("/delete/{propertyId}")
    public String deleteBookmark(@PathVariable Long propertyId, @AuthenticationPrincipal User user) {
        bookmarkService.deleteBookmarkProperty(user.getUsername(), propertyId);
        return "redirect:/bookmark";
    }
}