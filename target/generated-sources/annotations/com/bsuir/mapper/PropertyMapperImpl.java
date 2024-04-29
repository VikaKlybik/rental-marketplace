package com.bsuir.mapper;

import com.bsuir.dto.AttributeGroupResponse;
import com.bsuir.dto.AttributeResponse;
import com.bsuir.dto.AttributeValueResponse;
import com.bsuir.dto.GeolocationDataResponse;
import com.bsuir.dto.ImageResponse;
import com.bsuir.dto.PropertyCategoryResponse;
import com.bsuir.dto.PropertyRequest;
import com.bsuir.dto.PropertyResponse;
import com.bsuir.dto.UserDTO;
import com.bsuir.entity.Attribute;
import com.bsuir.entity.AttributeGroup;
import com.bsuir.entity.AttributeValue;
import com.bsuir.entity.GeolocationData;
import com.bsuir.entity.Image;
import com.bsuir.entity.Property;
import com.bsuir.entity.PropertyCategory;
import com.bsuir.entity.Role;
import com.bsuir.entity.User;
import com.bsuir.entity.UserDetails;
import com.bsuir.enums.PropertyStatus;
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
public class PropertyMapperImpl implements PropertyMapper {

    @Override
    public Property toProperty(PropertyRequest propertyRequest) {
        if ( propertyRequest == null ) {
            return null;
        }

        Property.PropertyBuilder property = Property.builder();

        property.propertyCategory( propertyRequestToPropertyCategory( propertyRequest ) );
        property.id( propertyRequest.getId() );
        property.title( propertyRequest.getTitle() );
        property.description( propertyRequest.getDescription() );
        property.price( propertyRequest.getPrice() );

        return property.build();
    }

    @Override
    public PropertyResponse toDto(Property property) {
        if ( property == null ) {
            return null;
        }

        PropertyResponse.PropertyResponseBuilder propertyResponse = PropertyResponse.builder();

        propertyResponse.user( userToUserDTO( property.getUser() ) );
        propertyResponse.id( property.getId() );
        propertyResponse.title( property.getTitle() );
        propertyResponse.description( property.getDescription() );
        if ( property.getPropertyStatus() != null ) {
            propertyResponse.propertyStatus( property.getPropertyStatus().name() );
        }
        propertyResponse.price( property.getPrice() );
        propertyResponse.propertyCategory( propertyCategoryToPropertyCategoryResponse( property.getPropertyCategory() ) );
        propertyResponse.geolocationData( geolocationDataToGeolocationDataResponse( property.getGeolocationData() ) );
        propertyResponse.createdAt( property.getCreatedAt() );
        propertyResponse.attributeValueList( attributeValueListToAttributeValueResponseList( property.getAttributeValueList() ) );
        propertyResponse.imageList( imageListToImageResponseList( property.getImageList() ) );

        return propertyResponse.build();
    }

    @Override
    public List<PropertyResponse> toListOfDto(List<Property> properties) {
        if ( properties == null ) {
            return null;
        }

        List<PropertyResponse> list = new ArrayList<PropertyResponse>( properties.size() );
        for ( Property property : properties ) {
            list.add( toDto( property ) );
        }

        return list;
    }

    @Override
    public PropertyRequest toPropertyRequest(Property property) {
        if ( property == null ) {
            return null;
        }

        PropertyRequest propertyRequest = new PropertyRequest();

        propertyRequest.setPropertyCategoryId( propertyPropertyCategoryId( property ) );
        propertyRequest.setId( property.getId() );
        propertyRequest.setTitle( property.getTitle() );
        propertyRequest.setDescription( property.getDescription() );
        propertyRequest.setPrice( property.getPrice() );

        return propertyRequest;
    }

    @Override
    public Property partialUpdate(PropertyResponse propertyResponse, Property property) {
        if ( propertyResponse == null ) {
            return property;
        }

        if ( propertyResponse.getId() != null ) {
            property.setId( propertyResponse.getId() );
        }
        if ( propertyResponse.getPropertyStatus() != null ) {
            property.setPropertyStatus( Enum.valueOf( PropertyStatus.class, propertyResponse.getPropertyStatus() ) );
        }
        if ( propertyResponse.getTitle() != null ) {
            property.setTitle( propertyResponse.getTitle() );
        }
        if ( propertyResponse.getDescription() != null ) {
            property.setDescription( propertyResponse.getDescription() );
        }
        if ( propertyResponse.getPrice() != null ) {
            property.setPrice( propertyResponse.getPrice() );
        }
        if ( propertyResponse.getUser() != null ) {
            if ( property.getUser() == null ) {
                property.setUser( User.builder().build() );
            }
            userDTOToUser( propertyResponse.getUser(), property.getUser() );
        }
        if ( propertyResponse.getPropertyCategory() != null ) {
            if ( property.getPropertyCategory() == null ) {
                property.setPropertyCategory( PropertyCategory.builder().build() );
            }
            propertyCategoryResponseToPropertyCategory( propertyResponse.getPropertyCategory(), property.getPropertyCategory() );
        }
        if ( propertyResponse.getGeolocationData() != null ) {
            if ( property.getGeolocationData() == null ) {
                property.setGeolocationData( GeolocationData.builder().build() );
            }
            geolocationDataResponseToGeolocationData( propertyResponse.getGeolocationData(), property.getGeolocationData() );
        }
        if ( propertyResponse.getCreatedAt() != null ) {
            property.setCreatedAt( propertyResponse.getCreatedAt() );
        }
        if ( property.getAttributeValueList() != null ) {
            List<AttributeValue> list = attributeValueResponseListToAttributeValueList( propertyResponse.getAttributeValueList() );
            if ( list != null ) {
                property.getAttributeValueList().clear();
                property.getAttributeValueList().addAll( list );
            }
        }
        else {
            List<AttributeValue> list = attributeValueResponseListToAttributeValueList( propertyResponse.getAttributeValueList() );
            if ( list != null ) {
                property.setAttributeValueList( list );
            }
        }
        if ( property.getImageList() != null ) {
            List<Image> list1 = imageResponseListToImageList( propertyResponse.getImageList() );
            if ( list1 != null ) {
                property.getImageList().clear();
                property.getImageList().addAll( list1 );
            }
        }
        else {
            List<Image> list1 = imageResponseListToImageList( propertyResponse.getImageList() );
            if ( list1 != null ) {
                property.setImageList( list1 );
            }
        }

        linkAttributeValueList( property );

        return property;
    }

    protected PropertyCategory propertyRequestToPropertyCategory(PropertyRequest propertyRequest) {
        if ( propertyRequest == null ) {
            return null;
        }

        PropertyCategory.PropertyCategoryBuilder propertyCategory = PropertyCategory.builder();

        propertyCategory.id( propertyRequest.getPropertyCategoryId() );

        return propertyCategory.build();
    }

    private String userUserDetailsFirstName(User user) {
        if ( user == null ) {
            return null;
        }
        UserDetails userDetails = user.getUserDetails();
        if ( userDetails == null ) {
            return null;
        }
        String firstName = userDetails.getFirstName();
        if ( firstName == null ) {
            return null;
        }
        return firstName;
    }

    private String userUserDetailsLastName(User user) {
        if ( user == null ) {
            return null;
        }
        UserDetails userDetails = user.getUserDetails();
        if ( userDetails == null ) {
            return null;
        }
        String lastName = userDetails.getLastName();
        if ( lastName == null ) {
            return null;
        }
        return lastName;
    }

    private String userUserDetailsEmail(User user) {
        if ( user == null ) {
            return null;
        }
        UserDetails userDetails = user.getUserDetails();
        if ( userDetails == null ) {
            return null;
        }
        String email = userDetails.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }

    private String userUserDetailsPhone(User user) {
        if ( user == null ) {
            return null;
        }
        UserDetails userDetails = user.getUserDetails();
        if ( userDetails == null ) {
            return null;
        }
        String phone = userDetails.getPhone();
        if ( phone == null ) {
            return null;
        }
        return phone;
    }

    private String userUserDetailsIconUrl(User user) {
        if ( user == null ) {
            return null;
        }
        UserDetails userDetails = user.getUserDetails();
        if ( userDetails == null ) {
            return null;
        }
        String iconUrl = userDetails.getIconUrl();
        if ( iconUrl == null ) {
            return null;
        }
        return iconUrl;
    }

    protected UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setFirstName( userUserDetailsFirstName( user ) );
        userDTO.setLastName( userUserDetailsLastName( user ) );
        userDTO.setEmail( userUserDetailsEmail( user ) );
        userDTO.setPhone( userUserDetailsPhone( user ) );
        userDTO.setIconUrl( userUserDetailsIconUrl( user ) );
        userDTO.setId( user.getId() );
        userDTO.setUsername( user.getUsername() );
        userDTO.setIsActive( user.getIsActive() );
        userDTO.setTotalPropertyCount( user.getTotalPropertyCount() );
        userDTO.setAllowPropertyCount( user.getAllowPropertyCount() );
        List<Role> list = user.getRoles();
        if ( list != null ) {
            userDTO.setRoles( new ArrayList<Role>( list ) );
        }

        return userDTO;
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

    protected GeolocationDataResponse geolocationDataToGeolocationDataResponse(GeolocationData geolocationData) {
        if ( geolocationData == null ) {
            return null;
        }

        GeolocationDataResponse.GeolocationDataResponseBuilder geolocationDataResponse = GeolocationDataResponse.builder();

        geolocationDataResponse.id( geolocationData.getId() );
        geolocationDataResponse.latitude( geolocationData.getLatitude() );
        geolocationDataResponse.longitude( geolocationData.getLongitude() );
        geolocationDataResponse.address( geolocationData.getAddress() );
        geolocationDataResponse.country( geolocationData.getCountry() );
        geolocationDataResponse.city( geolocationData.getCity() );

        return geolocationDataResponse.build();
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

    protected AttributeValueResponse attributeValueToAttributeValueResponse(AttributeValue attributeValue) {
        if ( attributeValue == null ) {
            return null;
        }

        AttributeValueResponse.AttributeValueResponseBuilder attributeValueResponse = AttributeValueResponse.builder();

        attributeValueResponse.id( attributeValue.getId() );
        attributeValueResponse.attribute( attributeToAttributeResponse( attributeValue.getAttribute() ) );
        attributeValueResponse.value( attributeValue.getValue() );

        return attributeValueResponse.build();
    }

    protected List<AttributeValueResponse> attributeValueListToAttributeValueResponseList(List<AttributeValue> list) {
        if ( list == null ) {
            return null;
        }

        List<AttributeValueResponse> list1 = new ArrayList<AttributeValueResponse>( list.size() );
        for ( AttributeValue attributeValue : list ) {
            list1.add( attributeValueToAttributeValueResponse( attributeValue ) );
        }

        return list1;
    }

    protected ImageResponse imageToImageResponse(Image image) {
        if ( image == null ) {
            return null;
        }

        ImageResponse.ImageResponseBuilder imageResponse = ImageResponse.builder();

        imageResponse.id( image.getId() );
        imageResponse.url( image.getUrl() );

        return imageResponse.build();
    }

    protected List<ImageResponse> imageListToImageResponseList(List<Image> list) {
        if ( list == null ) {
            return null;
        }

        List<ImageResponse> list1 = new ArrayList<ImageResponse>( list.size() );
        for ( Image image : list ) {
            list1.add( imageToImageResponse( image ) );
        }

        return list1;
    }

    private Long propertyPropertyCategoryId(Property property) {
        if ( property == null ) {
            return null;
        }
        PropertyCategory propertyCategory = property.getPropertyCategory();
        if ( propertyCategory == null ) {
            return null;
        }
        Long id = propertyCategory.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected void userDTOToUser(UserDTO userDTO, User mappingTarget) {
        if ( userDTO == null ) {
            return;
        }

        if ( userDTO.getId() != null ) {
            mappingTarget.setId( userDTO.getId() );
        }
        if ( userDTO.getUsername() != null ) {
            mappingTarget.setUsername( userDTO.getUsername() );
        }
        if ( userDTO.getIsActive() != null ) {
            mappingTarget.setIsActive( userDTO.getIsActive() );
        }
        if ( mappingTarget.getRoles() != null ) {
            List<Role> list = userDTO.getRoles();
            if ( list != null ) {
                mappingTarget.getRoles().clear();
                mappingTarget.getRoles().addAll( list );
            }
        }
        else {
            List<Role> list = userDTO.getRoles();
            if ( list != null ) {
                mappingTarget.setRoles( new ArrayList<Role>( list ) );
            }
        }
        if ( userDTO.getTotalPropertyCount() != null ) {
            mappingTarget.setTotalPropertyCount( userDTO.getTotalPropertyCount() );
        }
        if ( userDTO.getAllowPropertyCount() != null ) {
            mappingTarget.setAllowPropertyCount( userDTO.getAllowPropertyCount() );
        }
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

    protected void geolocationDataResponseToGeolocationData(GeolocationDataResponse geolocationDataResponse, GeolocationData mappingTarget) {
        if ( geolocationDataResponse == null ) {
            return;
        }

        if ( geolocationDataResponse.getId() != null ) {
            mappingTarget.setId( geolocationDataResponse.getId() );
        }
        if ( geolocationDataResponse.getLatitude() != null ) {
            mappingTarget.setLatitude( geolocationDataResponse.getLatitude() );
        }
        if ( geolocationDataResponse.getLongitude() != null ) {
            mappingTarget.setLongitude( geolocationDataResponse.getLongitude() );
        }
        if ( geolocationDataResponse.getAddress() != null ) {
            mappingTarget.setAddress( geolocationDataResponse.getAddress() );
        }
        if ( geolocationDataResponse.getCountry() != null ) {
            mappingTarget.setCountry( geolocationDataResponse.getCountry() );
        }
        if ( geolocationDataResponse.getCity() != null ) {
            mappingTarget.setCity( geolocationDataResponse.getCity() );
        }
    }

    protected PropertyCategory propertyCategoryResponseToPropertyCategory1(PropertyCategoryResponse propertyCategoryResponse) {
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
        attributeGroup.propertyCategory( propertyCategoryResponseToPropertyCategory1( attributeGroupResponse.getPropertyCategory() ) );

        return attributeGroup.build();
    }

    protected Attribute attributeResponseToAttribute(AttributeResponse attributeResponse) {
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

    protected AttributeValue attributeValueResponseToAttributeValue(AttributeValueResponse attributeValueResponse) {
        if ( attributeValueResponse == null ) {
            return null;
        }

        AttributeValue.AttributeValueBuilder attributeValue = AttributeValue.builder();

        attributeValue.id( attributeValueResponse.getId() );
        attributeValue.attribute( attributeResponseToAttribute( attributeValueResponse.getAttribute() ) );
        attributeValue.value( attributeValueResponse.getValue() );

        return attributeValue.build();
    }

    protected List<AttributeValue> attributeValueResponseListToAttributeValueList(List<AttributeValueResponse> list) {
        if ( list == null ) {
            return null;
        }

        List<AttributeValue> list1 = new ArrayList<AttributeValue>( list.size() );
        for ( AttributeValueResponse attributeValueResponse : list ) {
            list1.add( attributeValueResponseToAttributeValue( attributeValueResponse ) );
        }

        return list1;
    }

    protected Image imageResponseToImage(ImageResponse imageResponse) {
        if ( imageResponse == null ) {
            return null;
        }

        Image.ImageBuilder image = Image.builder();

        image.id( imageResponse.getId() );
        image.url( imageResponse.getUrl() );

        return image.build();
    }

    protected List<Image> imageResponseListToImageList(List<ImageResponse> list) {
        if ( list == null ) {
            return null;
        }

        List<Image> list1 = new ArrayList<Image>( list.size() );
        for ( ImageResponse imageResponse : list ) {
            list1.add( imageResponseToImage( imageResponse ) );
        }

        return list1;
    }
}
