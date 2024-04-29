package com.bsuir.entity;

import com.bsuir.dto.AttributeGroupResponse;
import com.bsuir.dto.AttributeResponse;
import com.bsuir.dto.PropertyCategoryResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-28T22:37:27+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (GraalVM Community)"
)
@Component
public class AttributeMapperImpl implements AttributeMapper {

    @Override
    public Attribute toEntity(AttributeResponse attributeResponse) {
        if ( attributeResponse == null ) {
            return null;
        }

        Attribute.AttributeBuilder attribute = Attribute.builder();

        attribute.id( attributeResponse.getId() );
        attribute.name( attributeResponse.getName() );
        attribute.dataType( attributeResponse.getDataType() );
        attribute.attributeGroup( attributeGroupResponseToAttributeGroup( attributeResponse.getAttributeGroup() ) );

        return attribute.build();
    }

    @Override
    public AttributeResponse toDto(Attribute attribute) {
        if ( attribute == null ) {
            return null;
        }

        AttributeResponse.AttributeResponseBuilder attributeResponse = AttributeResponse.builder();

        attributeResponse.id( attribute.getId() );
        attributeResponse.name( attribute.getName() );
        attributeResponse.dataType( attribute.getDataType() );
        attributeResponse.attributeGroup( attributeGroupToAttributeGroupResponse( attribute.getAttributeGroup() ) );

        return attributeResponse.build();
    }

    @Override
    public Attribute partialUpdate(AttributeResponse attributeResponse, Attribute attribute) {
        if ( attributeResponse == null ) {
            return attribute;
        }

        if ( attributeResponse.getId() != null ) {
            attribute.setId( attributeResponse.getId() );
        }
        if ( attributeResponse.getName() != null ) {
            attribute.setName( attributeResponse.getName() );
        }
        if ( attributeResponse.getDataType() != null ) {
            attribute.setDataType( attributeResponse.getDataType() );
        }
        if ( attributeResponse.getAttributeGroup() != null ) {
            if ( attribute.getAttributeGroup() == null ) {
                attribute.setAttributeGroup( AttributeGroup.builder().build() );
            }
            attributeGroupResponseToAttributeGroup1( attributeResponse.getAttributeGroup(), attribute.getAttributeGroup() );
        }

        return attribute;
    }

    @Override
    public List<AttributeResponse> toListOfDto(List<Attribute> attributes) {
        if ( attributes == null ) {
            return null;
        }

        List<AttributeResponse> list = new ArrayList<AttributeResponse>( attributes.size() );
        for ( Attribute attribute : attributes ) {
            list.add( toDto( attribute ) );
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

    protected AttributeGroup attributeGroupResponseToAttributeGroup(AttributeGroupResponse attributeGroupResponse) {
        if ( attributeGroupResponse == null ) {
            return null;
        }

        AttributeGroup.AttributeGroupBuilder attributeGroup = AttributeGroup.builder();

        attributeGroup.id( attributeGroupResponse.getId() );
        attributeGroup.groupName( attributeGroupResponse.getGroupName() );
        attributeGroup.propertyCategory( propertyCategoryResponseToPropertyCategory( attributeGroupResponse.getPropertyCategory() ) );

        return attributeGroup.build();
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

    protected AttributeGroupResponse attributeGroupToAttributeGroupResponse(AttributeGroup attributeGroup) {
        if ( attributeGroup == null ) {
            return null;
        }

        AttributeGroupResponse.AttributeGroupResponseBuilder attributeGroupResponse = AttributeGroupResponse.builder();

        attributeGroupResponse.id( attributeGroup.getId() );
        attributeGroupResponse.groupName( attributeGroup.getGroupName() );
        attributeGroupResponse.propertyCategory( propertyCategoryToPropertyCategoryResponse( attributeGroup.getPropertyCategory() ) );

        return attributeGroupResponse.build();
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

    protected void attributeGroupResponseToAttributeGroup1(AttributeGroupResponse attributeGroupResponse, AttributeGroup mappingTarget) {
        if ( attributeGroupResponse == null ) {
            return;
        }

        if ( attributeGroupResponse.getId() != null ) {
            mappingTarget.setId( attributeGroupResponse.getId() );
        }
        if ( attributeGroupResponse.getGroupName() != null ) {
            mappingTarget.setGroupName( attributeGroupResponse.getGroupName() );
        }
        if ( attributeGroupResponse.getPropertyCategory() != null ) {
            if ( mappingTarget.getPropertyCategory() == null ) {
                mappingTarget.setPropertyCategory( PropertyCategory.builder().build() );
            }
            propertyCategoryResponseToPropertyCategory1( attributeGroupResponse.getPropertyCategory(), mappingTarget.getPropertyCategory() );
        }
    }
}
