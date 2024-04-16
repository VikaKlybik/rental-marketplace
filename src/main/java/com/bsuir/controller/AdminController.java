package com.bsuir.controller;

import com.bsuir.dto.*;
import com.bsuir.entity.PropertyCategory;
import com.bsuir.entity.User;
import com.bsuir.service.PaymentService;
import com.bsuir.service.PropertyService;
import com.bsuir.service.RoleService;
import com.bsuir.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping(("/admin"))
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final PropertyService propertyService;
    private final PaymentService paymentService;
    private final RoleService roleService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String admin(Model model, @AuthenticationPrincipal User user) {
        addModelAttribute(model, user.getUsername());
        return "admin";
    }

    private void addModelAttribute(Model model, String exceptUsername) {
        Integer usersCount = userService.getUsersCount();
        model.addAttribute("usersCount", usersCount);

        Integer usersCountWithMoreThanOneProperty = userService.getUsersCountWithMoreThanOneProperty();
        model.addAttribute("usersCountWithMoreThanOneProperty", usersCountWithMoreThanOneProperty);

        BigDecimal revenueFromPublishProperty = paymentService.getRevenue();
        model.addAttribute("revenueFromPublishProperty", revenueFromPublishProperty);

        Integer propertyCount = propertyService.getPropertyCount();
        model.addAttribute("propertyCount", propertyCount);

        List<AttributeGroupResponse> attributeGroupResponses = propertyService.getAllAttributeGroup();
        model.addAttribute("attributeGroups", attributeGroupResponses);

        List<AttributeResponse> attributes = propertyService.getAllAttribute();
        model.addAttribute("attributes", attributes);

        model.addAttribute("newAttributeGroup", new AttributeGroupRequest());
        model.addAttribute("newAttribute", new AttributeRequest());

        List<UserDTO> userDTOList = userService.getAllUser(exceptUsername);
        model.addAttribute("users", userDTOList);

        List<String> roles = List.of(roleService.getAllRolesAsStringArray());
        model.addAttribute("systemRoles", roles);

        List<PropertyCategoryResponse> propertyCategories = propertyService.getAllPropertyCategory();
        model.addAttribute("propertyCategories", propertyCategories);

        model.addAttribute("newPropertyCategory", new PropertyCategory());

        model.addAttribute("propertyData", propertyService.getStaticsData());
        model.addAttribute("paymentData", paymentService.getStaticsData());
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String updateRoles(@RequestParam("roles") String[] roles, @RequestParam("username") String username) {
        userService.updateRole(roles, username);
        return "redirect:/admin";
    }

    @PostMapping("/property-category")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String addCarType(@ModelAttribute PropertyCategory propertyCategory) {
        propertyService.savePropertyCategory(propertyCategory);
        return "redirect:/admin";
    }

    @PostMapping("/attribute-group")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String addAttributeGroup(@ModelAttribute AttributeGroupRequest attributeGroupRequest) {
        propertyService.saveAttributeGroup(attributeGroupRequest);
        return "redirect:/admin";
    }


    @PostMapping("/attribute")
    public String addAttribute(@ModelAttribute AttributeRequest attributeRequest) {
        propertyService.saveAttribute(attributeRequest);
        return "redirect:/admin";
    }

}