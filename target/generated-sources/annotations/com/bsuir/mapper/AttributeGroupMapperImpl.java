package com.bsuir.mapper;

import com.bsuir.dto.AttributeGroupResponse;
import com.bsuir.dto.PropertyCategoryResponse;
import com.bsuir.entity.AttributeGroup;
import com.bsuir.entity.PropertyCategory;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-28T22:37:26+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (GraalVM Community)"
)
@Component
public class AttributeGroupMapperImpl implements AttributeGroupMapper {

    @Override
    public AttributeGroup toEntity(AttributeGroupResponse attributeGroupResponse) {
        if ( attributeGroupResponse == null ) {
            return null;
        }

        AttributeGroup.AttributeGroupBuilder attributeGroup = AttributeGroup.builder();

        attributeGroup.id( attributeGroupResponse.getId() );
        attributeGroup.groupName( attributeGroupResponse.getGroupName() );
        attributeGroup.propertyCategory( propertyCategoryResponseToPropertyCategory( attributeGroupResponse.getPropertyCategory() ) );

        return attributeGroup.build();
    }

    @Override
    public AttributeGroupResponse toDto(AttributeGroup attributeGroup) {
        if ( attributeGroup == null ) {
            return null;
        }

        AttributeGroupResponse.AttributeGroupResponseBuilder attributeGroupResponse = AttributeGroupResponse.builder();

        attributeGroupResponse.id( attributeGroup.getId() );
        attributeGroupResponse.groupName( attributeGroup.getGroupName() );
        attributeGroupResponse.propertyCategory( propertyCategoryToPropertyCategoryResponse( attributeGroup.getPropertyCategory() ) );

        return attributeGroupResponse.build();
    }

    @Override
    public AttributeGroup partialUpdate(AttributeGroupResponse attributeGroupResponse, AttributeGroup attributeGroup) {
        if ( attributeGroupResponse == null ) {
            return attributeGroup;
        }

        if ( attributeGroupResponse.getId() != null ) {
            attributeGroup.setId( attributeGroupResponse.getId() );
        }
        if ( attributeGroupResponse.getGroupName() != null ) {
            attributeGroup.setGroupName( attributeGroupResponse.getGroupName() );
        }
        if ( attributeGroupResponse.getPropertyCategory() != null ) {
            if ( attributeGroup.getPropertyCategory() == null ) {
                attributeGroup.setPropertyCategory( PropertyCategory.builder().build() );
            }
            propertyCategoryResponseToPropertyCategory1( attributeGroupResponse.getPropertyCategory(), attributeGroup.getPropertyCategory() );
        }

        return attributeGroup;
    }

    @Override
    public List<AttributeGroupResponse> toListOfDto(List<AttributeGroup> all) {
        if ( all == null ) {
            return null;
        }

        List<AttributeGroupResponse> list = new ArrayList<AttributeGroupResponse>( all.size() );
        for ( AttributeGroup attributeGroup : all ) {
            list.add( toDto( attributeGroup ) );
        }

        return list;
    }

    protected PropertyCategory propertyCategoryResponseToPropertyCategory(PropertyCategoryResponse propertyCategoryResponse) {
        if ( propertyCategoryResponse == null ) {
            return null;
        }

        PropertyCategory.PropertyCategoryBuilder propertyCategory = PropertyCategory.builder();

        propertyCategory.id( propertyCategoryResponse.getId() );
        propertyCategory.name( propertyCategoryResponse.getName() );

        return propertyCategory.build();
    }

    protected PropertyCategoryResponse propertyCategoryToPropertyCategoryResponse(PropertyCategory propertyCategory) {
        if ( propertyCategory == null ) {
            return null;
        }

        PropertyCategoryResponse.PropertyCategoryResponseBuilder propertyCategoryResponse = PropertyCategoryResponse.builder();

        propertyCategoryResponse.id( propertyCategory.getId() );
        propertyCategoryResponse.name( propertyCategory.getName() );

        return propertyCategoryResponse.build();
    }

    protected void propertyCategoryResponseToPropertyCategory1(PropertyCategoryResponse propertyCategoryResponse, PropertyCategory mappingTarget) {
        if ( propertyCategoryResponse == null ) {
            return;
        }

        if ( propertyCategoryResponse.getId() != null ) {
            mappingTarget.setId( propertyCategoryResponse.getId() );
        }
        if ( propertyCategoryResponse.getName() != null ) {
            mappingTarget.setName( propertyCategoryResponse.getName() );
        }
    }
}
