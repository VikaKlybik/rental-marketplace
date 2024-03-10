package com.bsuir.controller;

import com.bsuir.dto.AttributeResponse;
import com.bsuir.dto.AttributeValueRequest;
import com.bsuir.dto.AttributeValueResponse;
import com.bsuir.dto.ChatRequest;
import com.bsuir.dto.GeolocationDataRequest;
import com.bsuir.dto.ImageResponse;
import com.bsuir.dto.PropertyCategoryResponse;
import com.bsuir.dto.PropertyChangeStatusRequest;
import com.bsuir.dto.PropertyFilterParameter;
import com.bsuir.dto.PropertyRequest;
import com.bsuir.dto.PropertyResponse;
import com.bsuir.dto.RatingRequest;
import com.bsuir.dto.RatingResponse;
import com.bsuir.entity.User;
import com.bsuir.exception.PropertyNotFoundException;
import com.bsuir.service.GeolocationDataService;
import com.bsuir.service.GoogleDriverService;
import com.bsuir.service.PropertyImageService;
import com.bsuir.service.PropertyService;
import com.bsuir.service.RatingService;
import com.bsuir.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/property")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;
    private final UserService userService;
    private final HttpServletRequest request;
    private final PropertyImageService imageService;
    private final GoogleDriverService googleDriverService;
    private final RatingService ratingService;
    private final GeolocationDataService geolocationDataService;

    @GetMapping
    public String getAllProperty(PropertyFilterParameter propertyFilterParameter,@RequestParam(required = false) String search, Model model) {
        List<PropertyResponse> propertyResponseList = propertyService.getAllProperty(propertyFilterParameter, search);
        model.addAttribute("properties", propertyResponseList);
        model.addAttribute("cities", geolocationDataService.getUniqueCities());
        model.addAttribute("countries", geolocationDataService.getUniqueCountries());
        model.addAttribute("propertyCategories", propertyService.getAllPropertyCategory());
        model.addAttribute("maxPrice", calculateMaxPrice(propertyResponseList));
        model.addAttribute("propertyFilterParameter", propertyFilterParameter);
        return "property-list";
    }

    private Double calculateMaxPrice(List<PropertyResponse> propertyResponseList) {
        return propertyResponseList.stream()
                .sorted(Comparator.comparing(PropertyResponse::getPrice))
                .map(PropertyResponse::getPrice)
                .mapToDouble(BigDecimal::doubleValue)
                .max()
                .orElse(0.0f);
    }

    @GetMapping("{id}")
    public String getPropertyById(@PathVariable Long id, Model model, @AuthenticationPrincipal User user) {
        PropertyResponse propertyResponse = propertyService.getPropertyById(id);
        Long userId = propertyResponse.getUser().getId();
        List<RatingResponse> ratingList = ratingService.getAllRatingForUser(userId);
        RatingRequest ratingRequest = new RatingRequest();
        ratingRequest.setUserId(userId);
        boolean owner =false;
        if(user != null) {
            model.addAttribute("user",  userService.getUserByUsername(user.getUsername()));
            model.addAttribute("chatRequest", new ChatRequest(propertyResponse.getUser().getId(), user.getId()));
            if(user.getUsername().equals(propertyResponse.getUser().getUsername())) {
                owner = true;
            }
        }
        model.addAttribute("owner", owner);
        model.addAttribute("userRating", calculateUserRating(ratingList));
        model.addAttribute("newRating", ratingRequest);
        model.addAttribute("ratingList", ratingList);
        model.addAttribute("property", propertyResponse);
        return "property-details";
    }

    @GetMapping("/user/own")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String getUserProperty(Model model, @AuthenticationPrincipal User user) {
        List<PropertyResponse> propertyResponseList = propertyService.getAllUserProperty(user.getUsername());

        model.addAttribute("properties", propertyResponseList);
        return "user-property";
    }

    private Float calculateUserRating(List<RatingResponse> ratingList) {
        return (float) ratingList.stream()
                .mapToDouble(ratingResponse -> Double.valueOf(ratingResponse.getRating()))
                .average()
                .orElse(0);
    }

    @GetMapping("/create")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String getPropertyCreatePage(Model model, @AuthenticationPrincipal User user) {
        PropertyRequest propertyRequest = new PropertyRequest();
        propertyService.validateUserCreateProperty(user);

        model.addAttribute("property", propertyRequest);
        model.addAttribute("propertyCategories", getPropertyCategories());
        return "property-create";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String createProperty(Model model, @AuthenticationPrincipal User user, @Valid @ModelAttribute("property") PropertyRequest propertyRequest, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("propertyCategories", getPropertyCategories());
            return "property-create";
        }
        propertyService.validateUserCreateProperty(user);
        Long id = propertyService.saveProperty(propertyRequest, user.getUsername());
        return "redirect:/property/" + id;
    }

    @GetMapping("/create/{id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String getPropertyUpdatePage(Model model, @PathVariable Long id, @AuthenticationPrincipal User user) {
        PropertyResponse propertyResponse = propertyService.getPropertyById(id);
        validateUserOwn(user.getUsername(), propertyResponse);
        PropertyRequest propertyRequest = propertyService.getPropertyRequestForUpdate(id);

        propertyService.validateUserCreateProperty(user);
        model.addAttribute("property", propertyRequest);
        model.addAttribute("propertyCategories", getPropertyCategories());
        return "property-create";
    }

    @GetMapping("/create/address/{id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String getAddressUpdatePage(@PathVariable Long id, Model model, @AuthenticationPrincipal User user) {
        PropertyResponse property = propertyService.getPropertyById(id);
        validateUserOwn(user.getUsername(), property);
        model.addAttribute("property", property);
        return "property-create-address";
    }

    @PostMapping("/create/address/{id}")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<?> updateAddress(@PathVariable Long id, @RequestBody GeolocationDataRequest geolocationDataRequest, @AuthenticationPrincipal User user) {
        PropertyResponse property = propertyService.getPropertyById(id);
        validateUserOwn(user.getUsername(), property);
        propertyService.updateAddress(geolocationDataRequest, id);
        String redirectUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/property/" + id;
        RedirectView redirectView = new RedirectView(redirectUrl);
        return ResponseEntity.ok().body(redirectView);
    }

    @GetMapping("/create/image/{id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String getUpdateImagePage(@PathVariable Long id, Model model, @AuthenticationPrincipal User user) {
        PropertyResponse property = propertyService.getPropertyById(id);
        validateUserOwn(user.getUsername(), property);
        List<ImageResponse> images = imageService.getAllImagesByPropertyId(id);

        model.addAttribute("property", property);
        model.addAttribute("propertyId", id);
        model.addAttribute("images", images);
        return "property-image-updates";
    }

    @GetMapping("/delete/image/{imageId}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String deleteImage(@PathVariable Long imageId) {

        Long propertyId = imageService.deleteImage(imageId);
        return "redirect:/property/create/image/" + propertyId;
    }

    @PostMapping("/create/image/{id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String uploadIconUser(@RequestParam("file") MultipartFile file, @PathVariable Long id) {
        googleDriverService.uploadPropertyImage(file, id);
        return "redirect:/property/create/image/" + id;
    }

    @GetMapping("/create/attribute/{id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String getCreateAttributesPage(@PathVariable Long id, Model model, @AuthenticationPrincipal User user) {
        List<AttributeResponse> attributeResponseList = propertyService.getAllAttributeForProperty(id);
        List<AttributeValueResponse> attributeValueResponseList = propertyService.getAllAttributeValueForProperty(id);
        List<AttributeResponse> afterDeleted = deleteRepeated(attributeResponseList, attributeValueResponseList);
        PropertyResponse property = propertyService.getPropertyById(id);
        validateUserOwn(user.getUsername(), property);

        model.addAttribute("property", property);
        model.addAttribute("attributeList", afterDeleted);
        model.addAttribute("attributeValueList", attributeValueResponseList);
        return "property-attribute";
    }

    @PostMapping("/create/attribute/{id}")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity.BodyBuilder saveParameter(@PathVariable Long id, @RequestBody AttributeValueRequest attributeValueRequest, @AuthenticationPrincipal User user) {
        PropertyResponse property = propertyService.getPropertyById(id);
        validateUserOwn(user.getUsername(), property);

        propertyService.saveValueForProperty(id, attributeValueRequest);
        return ResponseEntity.ok();
    }

    @PutMapping("/status")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity.BodyBuilder updateStatus(@RequestBody PropertyChangeStatusRequest property) {

        propertyService.updatePropertyStatus(property);
        return ResponseEntity.ok();
    }

    private List<PropertyCategoryResponse> getPropertyCategories() {
        return propertyService.getAllPropertyCategory();
    }

    private List<AttributeResponse> deleteRepeated(List<AttributeResponse> attributeResponseList, List<AttributeValueResponse> attributeValueResponseList) {
        List<AttributeResponse> removeAttributeList = attributeValueResponseList.stream()
                .map(AttributeValueResponse::getAttribute)
                .toList();

        return attributeResponseList.stream()
                .filter((attributeResponse -> removeAttributeList.stream()
                        .noneMatch(removeAttribute -> attributeResponse.getId().equals(removeAttribute.getId()))))
                .toList();
    }

    private void validateUserOwn(String username, PropertyResponse propertyResponse) {
        if(!propertyResponse.getUser().getUsername().equals(username)) {
            throw new PropertyNotFoundException();
        }
    }
}