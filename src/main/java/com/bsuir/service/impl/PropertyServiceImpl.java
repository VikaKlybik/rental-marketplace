package com.bsuir.service.impl;

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
import com.bsuir.entity.Attribute;
import com.bsuir.entity.AttributeGroup;
import com.bsuir.entity.AttributeMapper;
import com.bsuir.entity.AttributeValue;
import com.bsuir.entity.GeolocationData;
import com.bsuir.entity.Property;
import com.bsuir.entity.PropertyCategory;
import com.bsuir.entity.User;
import com.bsuir.enums.PropertyStatus;
import com.bsuir.exception.PropertyCreateNotAllowException;
import com.bsuir.exception.PropertyNotFoundException;
import com.bsuir.exception.UserNotFoundException;
import com.bsuir.mapper.AttributeGroupMapper;
import com.bsuir.mapper.AttributeValueMapper;
import com.bsuir.mapper.GeolocationDataMapper;
import com.bsuir.mapper.PropertyCategoryMapper;
import com.bsuir.mapper.PropertyMapper;
import com.bsuir.repository.AttributeGroupRepository;
import com.bsuir.repository.AttributeRepository;
import com.bsuir.repository.AttributeValueRepository;
import com.bsuir.repository.GeolocationDataRepository;
import com.bsuir.repository.PropertyCategoryRepository;
import com.bsuir.repository.PropertyRepository;
import com.bsuir.repository.UserRepository;
import com.bsuir.service.PropertyService;
import com.bsuir.service.UserService;
import com.bsuir.util.PropertySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyCategoryRepository propertyCategoryRepository;
    private final PropertyMapper propertyMapper;
    private final PropertyCategoryMapper propertyCategoryMapper;
    private final UserRepository userRepository;
    private final GeolocationDataMapper geolocationDataMapper;
    private final GeolocationDataRepository geolocationDataRepository;
    private final AttributeMapper attributeMapper;
    private final AttributeValueRepository attributeValueRepository;
    private final AttributeValueMapper attributeValueMapper;
    private final UserService userService;
    private final AttributeGroupRepository attributeGroupRepository;
    private final AttributeGroupMapper attributeGroupMapper;
    private final AttributeRepository attributeRepository;

    @Override
    public List<PropertyResponse> getAllProperty(PropertyFilterParameter propertyFilterParameter, String search) {
        List<Property> propertyList = propertyRepository.findAll(
                PropertySpecification.createSpecificationByPropertyFilterParameter(propertyFilterParameter, search)
        );
        return propertyMapper.toListOfDto(propertyList);
    }


    @Override
    public PropertyResponse getPropertyById(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(PropertyNotFoundException::new);
        return propertyMapper.toDto(property);
    }

    @Override
    public List<PropertyCategoryResponse> getAllPropertyCategory() {
        List<PropertyCategory> propertyCategoryList = propertyCategoryRepository.findAll();
        return propertyCategoryMapper.toListOfDto(propertyCategoryList);
    }

    @Override
    @Transactional
    public Long saveProperty(PropertyRequest propertyRequest, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        if(user.getAllowPropertyCount() <= 0) {
            throw new PropertyCreateNotAllowException(username);
        }

        Property property = getOrCreateProperty(propertyRequest);
        user.setAllowPropertyCount(user.getAllowPropertyCount()-1);
        user.setTotalPropertyCount(user.getTotalPropertyCount()+1);
        property.setUser(user);
        property.setPropertyStatus(PropertyStatus.CLOSED);
        return propertyRepository.save(property)
                .getId();
    }

    private Property getOrCreateProperty(PropertyRequest propertyRequest) {
        if(propertyRequest.getId() == null){
            return propertyMapper.toProperty(propertyRequest);
        }

        Property property = propertyRepository.findById(propertyRequest.getId())
                .orElseThrow(PropertyNotFoundException::new);
        property.setTitle(propertyRequest.getTitle());
        property.setDescription(propertyRequest.getDescription());
        property.setPrice(propertyRequest.getPrice());
        return property;
    }

    @Override
    @Transactional
    public void updateAddress(GeolocationDataRequest geolocationDataRequest, Long id) {
        GeolocationData geolocationData = geolocationDataMapper.toGeolocationData(geolocationDataRequest);
        Property property = propertyRepository.findById(id)
                .orElseThrow(PropertyNotFoundException::new);

        property.setPropertyStatus(PropertyStatus.AVAILABLE);
        property.setGeolocationData(
                geolocationDataRepository.save(geolocationData)
        );
    }

    @Override
    public List<AttributeResponse> getAllAttributeForProperty(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(PropertyNotFoundException::new);
        PropertyCategory propertyCategory = property.getPropertyCategory();
        List<Attribute> attributes = propertyCategory.getAttributeGroups().stream()
                .flatMap(attributeGroup -> attributeGroup.getAttributes().stream())
                .toList();
        return attributeMapper.toListOfDto(attributes);
    }

    @Override
    public List<AttributeValueResponse> getAllAttributeValueForProperty(Long id) {
        List<AttributeValue> attributeValueList = attributeValueRepository.findAllByPropertyId(id);

        return attributeValueMapper.toListOfDto(attributeValueList);
    }

    @Override
    public void saveValueForProperty(Long id, AttributeValueRequest attributeValueRequest) {
        AttributeValue attributeValue = getOrCreateAttributeValue(id, attributeValueRequest);
        attributeValue.setValue(attributeValueRequest.getAttributeValue());
        attributeValueRepository.save(attributeValue);
    }

    private AttributeValue getOrCreateAttributeValue(Long id, AttributeValueRequest attributeValueRequest) {
        Long attributeId = Long.parseLong(attributeValueRequest.getAttributeId());
        return attributeValueRepository.findByPropertyIdAndAttributeId(
                id, attributeId
                ).orElseGet(()->{
                    Attribute attribute = new Attribute();
                    attribute.setId(attributeId);

                    Property property = new Property();
                    property.setId(id);

                    return AttributeValue.builder()
                            .attribute(attribute)
                            .property(property)
                            .build();
                });
    }

    @Override
    public PropertyRequest getPropertyRequestForUpdate(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(PropertyNotFoundException::new);

        return propertyMapper.toPropertyRequest(property);
    }

    @Override
    public List<PropertyResponse> getAllUserProperty(String username) {
        Long userId = userService.getUserByUsername(username)
                .getId();
        List<Property> propertiesList = propertyRepository.findAllByUserId(userId);
        return propertyMapper.toListOfDto(propertiesList);
    }

    @Override
    @Transactional
    public void updatePropertyStatus(PropertyChangeStatusRequest propertyChangeStatusRequest) {
        Property property = propertyRepository.findById(propertyChangeStatusRequest.getPropertyId())
                .orElseThrow(PropertyNotFoundException::new);
        property.setPropertyStatus(PropertyStatus.valueOf(propertyChangeStatusRequest.getStatus()));
    }

    @Override
    public void validateUserCreateProperty(User user) {
        Integer allowPropertyCount = userService.getUserByUsername(user.getUsername())
                .getAllowPropertyCount();
        if(allowPropertyCount <= 0) {
            throw new PropertyCreateNotAllowException(user.getUsername());
        }
    }

    @Override
    public Boolean isPropertyExist(Long propertyId) {
        return propertyRepository.existsById(propertyId);
    }

    @Override
    public Integer getPropertyCount() {
        return propertyRepository.findAll().size();
    }

    @Override
    public Map<String, Integer> getStaticsData() {
        Map<String, Integer> data = new LinkedHashMap<>();
        LocalDateTime now = LocalDateTime.now();
        for(int i=0; i<6; i++) {
            LocalDateTime startOfMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
            LocalDateTime endOfMonth = now.withDayOfMonth(now.getMonth().maxLength()).withHour(23).withMinute(59).withSecond(59);
            data.put(
                    now.getMonth().getDisplayName(TextStyle.SHORT, new Locale("ru", "RU")),
                    propertyRepository.findAllByCreatedAtBetween(startOfMonth, endOfMonth)
                            .size()
            );
            now = now.minusMonths(1);
        }
        return data;
    }

    @Override
    public void savePropertyCategory(PropertyCategory propertyCategory) {
        propertyCategoryRepository.save(propertyCategory);
    }

    @Override
    public List<AttributeGroupResponse> getAllAttributeGroup() {
        return attributeGroupMapper.toListOfDto(attributeGroupRepository.findAll());
    }

    @Override
    public List<AttributeResponse> getAllAttribute() {
        return attributeMapper.toListOfDto(
                attributeRepository.findAll()
        );
    }

    @Override
    public void saveAttributeGroup(AttributeGroupRequest attributeGroupRequest) {
        PropertyCategory propertyCategory = new PropertyCategory();
        propertyCategory.setId(attributeGroupRequest.getPropertyCategoryId());

        AttributeGroup attributeGroup = AttributeGroup.builder()
                .groupName(attributeGroupRequest.getGroupName())
                .propertyCategory(propertyCategory)
                .build();
        attributeGroupRepository.save(attributeGroup);
    }

    @Override
    public void saveAttribute(AttributeRequest attributeRequest) {
        AttributeGroup attributeGroup = new AttributeGroup();
        attributeGroup.setId(attributeRequest.getAttributeGroupId());

        Attribute attribute = Attribute.builder()
                .name(attributeRequest.getName())
                .dataType(attributeRequest.getDataType())
                .attributeGroup(attributeGroup)
                .build();
        attributeRepository.save(attribute);
    }

    @Override
    public List<PropertyResponse> getFirstThreeProperty() {
        List<Property> properties = propertyRepository.findAllByPropertyStatus(PropertyStatus.AVAILABLE)
                .stream()
                .limit(6)
                .toList();
        return propertyMapper.toListOfDto(properties);
    }
}