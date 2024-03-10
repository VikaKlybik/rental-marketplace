package com.bsuir.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttributeGroupResponse {
    private Long id;
    private String groupName;
    private PropertyCategoryResponse propertyCategory;
}