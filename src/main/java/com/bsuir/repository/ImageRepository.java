package com.bsuir.repository;

import com.bsuir.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByPropertyId(Long propertyId);
}