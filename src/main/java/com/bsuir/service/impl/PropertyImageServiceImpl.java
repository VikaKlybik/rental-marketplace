package com.bsuir.service.impl;

import com.bsuir.dto.ImageResponse;
import com.bsuir.entity.Image;
import com.bsuir.entity.Property;
import com.bsuir.exception.ImageNotFoundException;
import com.bsuir.mapper.ImageMapper;
import com.bsuir.repository.ImageRepository;
import com.bsuir.service.PropertyImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyImageServiceImpl implements PropertyImageService {

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    @Override
    public List<ImageResponse> getAllImagesByPropertyId(Long propertyId) {
        List<Image> images = imageRepository.findAllByPropertyId(propertyId);

        return imageMapper.toListOfDto(images);
    }

    @Override
    public Long deleteImage(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(ImageNotFoundException::new);
        Long propertyId = image.getProperty().getId();

        imageRepository.delete(image);
        return propertyId;
    }

    @Override
    public void createImage(String fileId, Long propertyId) {
        Property property = new Property();
        property.setId(propertyId);
        Image image = Image.builder()
                .url(fileId)
                .property(property)
                .build();
        imageRepository.save(image);
    }
}