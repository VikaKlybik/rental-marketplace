package com.bsuir.service;

import com.bsuir.dto.ImageResponse;

import java.util.List;

public interface PropertyImageService {
    List<ImageResponse> getAllImagesByPropertyId(Long propertyId);
    Long deleteImage(Long id);

    void createImage(String fileId, Long propertyId);
}