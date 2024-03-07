package com.bsuir.repository;

import com.bsuir.entity.PropertyCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyCategoryRepository extends JpaRepository<PropertyCategory, Long> {
}