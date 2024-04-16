package com.bsuir.controller;

import com.bsuir.dto.RatingRequest;
import com.bsuir.entity.User;
import com.bsuir.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/rating")
public class RatingController {
    private final RatingService ratingService;

    @PostMapping("{propertyId}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String createRating(@PathVariable Long propertyId, @ModelAttribute("newRating") RatingRequest ratingRequest, @AuthenticationPrincipal User user) {
        ratingService.createRating(ratingRequest, user.getUsername());
        return "redirect:/property/" + propertyId;
    }

    @GetMapping("/delete/{propertyId}/{ratingId}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String deleteRating(@PathVariable Long propertyId, @PathVariable Long ratingId) {
        ratingService.deleteRating(ratingId);
        return "redirect:/property/" + propertyId;
    }
}