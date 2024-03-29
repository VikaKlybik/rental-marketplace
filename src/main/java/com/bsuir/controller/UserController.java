package com.bsuir.controller;

import com.bsuir.dto.RatingResponse;
import com.bsuir.dto.UserDTO;
import com.bsuir.entity.User;
import com.bsuir.service.GoogleDriverService;
import com.bsuir.service.RatingService;
import com.bsuir.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

import static com.bsuir.constant.RentalPropertiesConstants.DefaultValue.PRICE_FOR_PUBLISH;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user-info")
public class UserController {
    private final UserService userService;
    private final RatingService ratingService;
    private final GoogleDriverService googleDriverService;
    @Value("${stripe.public.key}")
    private String stripePublicKey;

    @GetMapping
    public String getUserById(Model model, @AuthenticationPrincipal User user) {
        UserDTO userDTO = userService.getUserByUsername(user.getUsername());
        model.addAttribute("user", userDTO);
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("amount", PRICE_FOR_PUBLISH.multiply(new BigDecimal(100)).intValue());
        return "user";
    }

    @GetMapping("{id}")
    public String getUserPageById(Model model, @PathVariable Long id) {
        UserDTO userResponse = userService.getUserById(id);
        List<RatingResponse> ratingList = ratingService.getAllRatingForUser(id);

        model.addAttribute("user", userResponse);
        model.addAttribute("ratingList", ratingList);
        model.addAttribute("userRating", calculateUserRating(ratingList));
        return "user-overview";
    }

    private Float calculateUserRating(List<RatingResponse> ratingList) {
        return (float) ratingList.stream()
                .mapToDouble(ratingResponse -> Double.valueOf(ratingResponse.getRating()))
                .average()
                .orElse(0);
    }

    @PostMapping
    public String saveUser(@Valid @ModelAttribute("user") UserDTO user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user";
        }
        userService.saveUser(user);
        return "redirect:/user-info";
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public UserDTO disableUser(@PathVariable Long id) {
        return userService.disableUser(id);
    }

    @PostMapping("/upload/icon-user")
    public String uploadIconUser(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal User user) {
        googleDriverService.uploadUserIcon(file, user.getUsername());
        return "redirect:/user-info";
    }
}