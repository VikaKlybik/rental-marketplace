package com.bsuir.mapper;

import com.bsuir.dto.PropertyCategoryResponse;
import com.bsuir.entity.PropertyCategory;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PropertyCategoryMapper {

    PropertyCategoryResponse toDto(PropertyCategory propertyCategory);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PropertyCategory partialUpdate(PropertyCategoryResponse propertyCategoryResponse, @MappingTarget PropertyCategory propertyCategory);

    List<PropertyCategoryResponse> toListOfDto(List<PropertyCategory> propertyCategoryList);
}