package com.bsuir.controller;

import com.bsuir.dto.PropertyResponse;
import com.bsuir.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomePageController {

    private final PropertyService propertyService;

    @GetMapping("/home")
    private String home(Model model) {
        List<PropertyResponse> propertyResponses = propertyService.getFirstThreeProperty();
        model.addAttribute("properties", propertyResponses);
        return "home";
    }

    @GetMapping
    private String defaultHome() {
        return "redirect:/home";
    }
}