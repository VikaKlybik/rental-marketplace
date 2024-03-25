package com.bsuir.mapper;

import com.bsuir.dto.AttributeGroupResponse;
import com.bsuir.entity.AttributeGroup;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AttributeGroupMapper {
    AttributeGroup toEntity(AttributeGroupResponse attributeGroupResponse);

    AttributeGroupResponse toDto(AttributeGroup attributeGroup);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AttributeGroup partialUpdate(AttributeGroupResponse attributeGroupResponse, @MappingTarget AttributeGroup attributeGroup);

    List<AttributeGroupResponse> toListOfDto(List<AttributeGroup> all);
}