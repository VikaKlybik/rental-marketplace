package com.bsuir.mapper;

import com.bsuir.dto.PropertyRequest;
import com.bsuir.dto.PropertyResponse;
import com.bsuir.entity.Property;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PropertyMapper {

    @AfterMapping
    default void linkAttributeValueList(@MappingTarget Property property) {
        property.getAttributeValueList().forEach(attributeValueList -> attributeValueList.setProperty(property));
    }

    @Mapping(target = "propertyStatus", ignore = true) // You may set propertyStatus as needed
    @Mapping(source = "propertyCategoryId", target = "propertyCategory.id")
    Property toProperty(PropertyRequest propertyRequest);


    @Mapping(source = "user.userDetails.firstName", target = "user.firstName")
    @Mapping(source = "user.userDetails.lastName", target = "user.lastName")
    @Mapping(source = "user.userDetails.email", target = "user.email")
    @Mapping(source = "user.userDetails.phone", target = "user.phone")
    @Mapping(source = "user.userDetails.iconUrl", target = "user.iconUrl")
    PropertyResponse toDto(Property property);
    List<PropertyResponse> toListOfDto(List<Property> properties);

    @Mapping(source = "propertyCategory.id", target = "propertyCategoryId")
    PropertyRequest toPropertyRequest(Property property);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Property partialUpdate(PropertyResponse propertyResponse, @MappingTarget Property property);

}