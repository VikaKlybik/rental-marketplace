package com.bsuir.service;

import com.bsuir.dto.AttributeGroupRequest;
import com.bsuir.dto.AttributeGroupResponse;
import com.bsuir.dto.AttributeRequest;
import com.bsuir.dto.AttributeResponse;
import com.bsuir.dto.AttributeValueRequest;
import com.bsuir.dto.AttributeValueResponse;
import com.bsuir.dto.GeolocationDataRequest;
import com.bsuir.dto.PropertyCategoryResponse;
import com.bsuir.dto.PropertyChangeStatusRequest;
import com.bsuir.dto.PropertyFilterParameter;
import com.bsuir.dto.PropertyRequest;
import com.bsuir.dto.PropertyResponse;
import com.bsuir.entity.PropertyCategory;
import com.bsuir.entity.User;

import java.util.List;
import java.util.Map;

public interface PropertyService {
    List<PropertyResponse> getAllProperty(PropertyFilterParameter propertyFilterParameter, String search);

    PropertyResponse getPropertyById(Long id);

    Boolean isPropertyExist(Long propertyId);
    List<PropertyCategoryResponse> getAllPropertyCategory();

    Long saveProperty(PropertyRequest propertyRequest, String username);

    void updateAddress(GeolocationDataRequest geolocationDataRequest, Long id);

    List<AttributeResponse> getAllAttributeForProperty(Long id);

    List<AttributeValueResponse> getAllAttributeValueForProperty(Long id);

    void saveValueForProperty(Long id, AttributeValueRequest attributeValueRequest);

    PropertyRequest getPropertyRequestForUpdate(Long id);

    List<PropertyResponse> getAllUserProperty(String username);

    void updatePropertyStatus(PropertyChangeStatusRequest property);

    void validateUserCreateProperty(User user);

    Integer getPropertyCount();
    Map<String, Integer> getStaticsData();

    void savePropertyCategory(PropertyCategory propertyCategory);

    List<AttributeGroupResponse> getAllAttributeGroup();

    List<AttributeResponse> getAllAttribute();

    void saveAttributeGroup(AttributeGroupRequest attributeGroupRequest);

    void saveAttribute(AttributeRequest attributeRequest);

    List<PropertyResponse> getFirstThreeProperty();
}