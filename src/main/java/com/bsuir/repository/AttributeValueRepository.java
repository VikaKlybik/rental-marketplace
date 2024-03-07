package com.bsuir.repository;

import com.bsuir.entity.AttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttributeValueRepository extends JpaRepository<AttributeValue, Long> {
    List<AttributeValue> findAllByPropertyId(Long propertyId);
    Optional<AttributeValue> findByPropertyIdAndAttributeId(Long propertyId, Long attributeId);
}