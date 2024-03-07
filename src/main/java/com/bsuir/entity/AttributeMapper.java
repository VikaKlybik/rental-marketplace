package com.bsuir.entity;

import com.bsuir.dto.AttributeResponse;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttributeMapper {
    Attribute toEntity(AttributeResponse attributeResponse);

    AttributeResponse toDto(Attribute attribute);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Attribute partialUpdate(AttributeResponse attributeResponse, @MappingTarget Attribute attribute);

    List<AttributeResponse> toListOfDto(List<Attribute> attributes);
}