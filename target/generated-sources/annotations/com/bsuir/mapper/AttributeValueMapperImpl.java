package com.bsuir.mapper;

import com.bsuir.dto.AttributeGroupResponse;
import com.bsuir.dto.AttributeResponse;
import com.bsuir.dto.AttributeValueResponse;
import com.bsuir.dto.PropertyCategoryResponse;
import com.bsuir.entity.Attribute;
import com.bsuir.entity.AttributeGroup;
import com.bsuir.entity.AttributeValue;
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
public class AttributeValueMapperImpl implements AttributeValueMapper {

    @Override
    public AttributeValueResponse toDto(AttributeValue attributeValue) {
        if ( attributeValue == null ) {
            return null;
        }

        AttributeValueResponse.AttributeValueResponseBuilder attributeValueResponse = AttributeValueResponse.builder();

        attributeValueResponse.id( attributeValue.getId() );
        attributeValueResponse.attribute( attributeToAttributeResponse( attributeValue.getAttribute() ) );
        attributeValueResponse.value( attributeValue.getValue() );

        return attributeValueResponse.build();
    }

    @Override
    public AttributeValue partialUpdate(AttributeValueResponse attributeValueResponse, AttributeValue attributeValue) {
        if ( attributeValueResponse == null ) {
            return attributeValue;
        }

        if ( attributeValueResponse.getId() != null ) {
            attributeValue.setId( attributeValueResponse.getId() );
        }
        if ( attributeValueResponse.getAttribute() != null ) {
            if ( attributeValue.getAttribute() == null ) {
                attributeValue.setAttribute( Attribute.builder().build() );
            }
            attributeResponseToAttribute( attributeValueResponse.getAttribute(), attributeValue.getAttribute() );
        }
        if ( attributeValueResponse.getValue() != null ) {
            attributeValue.setValue( attributeValueResponse.getValue() );
        }

        return attributeValue;
    }

    @Override
    public List<AttributeValueResponse> toListOfDto(List<AttributeValue> attributeValueList) {
        if ( attributeValueList == null ) {
            return null;
        }

        List<AttributeValueResponse> list = new ArrayList<AttributeValueResponse>( attributeValueList.size() );
        for ( AttributeValue attributeValue : attributeValueList ) {
            list.add( toDto( attributeValue ) );
        }

        return list;
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

    protected AttributeResponse attributeToAttributeResponse(Attribute attribute) {
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

    protected void propertyCategoryResponseToPropertyCategory(PropertyCategoryResponse propertyCategoryResponse, PropertyCategory mappingTarget) {
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

    protected void attributeGroupResponseToAttributeGroup(AttributeGroupResponse attributeGroupResponse, AttributeGroup mappingTarget) {
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
            propertyCategoryResponseToPropertyCategory( attributeGroupResponse.getPropertyCategory(), mappingTarget.getPropertyCategory() );
        }
    }

    protected void attributeResponseToAttribute(AttributeResponse attributeResponse, Attribute mappingTarget) {
        if ( attributeResponse == null ) {
            return;
        }

        if ( attributeResponse.getId() != null ) {
            mappingTarget.setId( attributeResponse.getId() );
        }
        if ( attributeResponse.getName() != null ) {
            mappingTarget.setName( attributeResponse.getName() );
        }
        if ( attributeResponse.getDataType() != null ) {
            mappingTarget.setDataType( attributeResponse.getDataType() );
        }
        if ( attributeResponse.getAttributeGroup() != null ) {
            if ( mappingTarget.getAttributeGroup() == null ) {
                mappingTarget.setAttributeGroup( AttributeGroup.builder().build() );
            }
            attributeGroupResponseToAttributeGroup( attributeResponse.getAttributeGroup(), mappingTarget.getAttributeGroup() );
        }
    }
}
