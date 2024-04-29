package com.bsuir.mapper;

import com.bsuir.dto.PropertyCategoryResponse;
import com.bsuir.entity.PropertyCategory;
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
public class PropertyCategoryMapperImpl implements PropertyCategoryMapper {

    @Override
    public PropertyCategoryResponse toDto(PropertyCategory propertyCategory) {
        if ( propertyCategory == null ) {
            return null;
        }

        PropertyCategoryResponse.PropertyCategoryResponseBuilder propertyCategoryResponse = PropertyCategoryResponse.builder();

        propertyCategoryResponse.id( propertyCategory.getId() );
        propertyCategoryResponse.name( propertyCategory.getName() );

        return propertyCategoryResponse.build();
    }

    @Override
    public PropertyCategory partialUpdate(PropertyCategoryResponse propertyCategoryResponse, PropertyCategory propertyCategory) {
        if ( propertyCategoryResponse == null ) {
            return propertyCategory;
        }

        if ( propertyCategoryResponse.getId() != null ) {
            propertyCategory.setId( propertyCategoryResponse.getId() );
        }
        if ( propertyCategoryResponse.getName() != null ) {
            propertyCategory.setName( propertyCategoryResponse.getName() );
        }

        return propertyCategory;
    }

    @Override
    public List<PropertyCategoryResponse> toListOfDto(List<PropertyCategory> propertyCategoryList) {
        if ( propertyCategoryList == null ) {
            return null;
        }

        List<PropertyCategoryResponse> list = new ArrayList<PropertyCategoryResponse>( propertyCategoryList.size() );
        for ( PropertyCategory propertyCategory : propertyCategoryList ) {
            list.add( toDto( propertyCategory ) );
        }

        return list;
    }
}
