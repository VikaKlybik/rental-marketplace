package com.bsuir.repository;

import com.bsuir.entity.Property;
import com.bsuir.enums.PropertyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> {
    List<Property> findAllByUserId(Long userId);
    List<Property> findAllByCreatedAtBetween(LocalDateTime startOfMonth, LocalDateTime endOfMonth);
    List<Property> findAllByPropertyStatus(PropertyStatus status);
}