package com.bsuir.mapper;

import com.bsuir.dto.ImageResponse;
import com.bsuir.entity.Image;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ImageMapper {

    ImageResponse toDto(Image image);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Image partialUpdate(ImageResponse imageResponse, @MappingTarget Image image);

    List<ImageResponse> toListOfDto(List<Image> images);
}