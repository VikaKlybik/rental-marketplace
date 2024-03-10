package com.bsuir.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyResponse {
    private Long id;
    private String title;
    private String description;
    private String propertyStatus;
    private BigDecimal price;
    private PropertyCategoryResponse propertyCategory;
    private GeolocationDataResponse geolocationData;
    private LocalDateTime createdAt;
    private UserDTO user;
    private List<AttributeValueResponse> attributeValueList;
    private List<ImageResponse> imageList;
    private List<RatingResponse> ratingResponseList;
}