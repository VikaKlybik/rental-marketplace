package com.bsuir.mapper;

import com.bsuir.dto.AttributeValueResponse;
import com.bsuir.entity.AttributeValue;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttributeValueMapper {

    AttributeValueResponse toDto(AttributeValue attributeValue);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AttributeValue partialUpdate(AttributeValueResponse attributeValueResponse, @MappingTarget AttributeValue attributeValue);

    List<AttributeValueResponse> toListOfDto(List<AttributeValue> attributeValueList);
}