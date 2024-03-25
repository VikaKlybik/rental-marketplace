package com.bsuir.mapper;

import com.bsuir.dto.RatingResponse;
import com.bsuir.entity.Rating;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface RatingMapper {
    @Mapping(source = "user.userDetails.firstName", target = "user.firstName")
    @Mapping(source = "user.userDetails.lastName", target = "user.lastName")
    @Mapping(source = "user.userDetails.email", target = "user.email")
    @Mapping(source = "user.userDetails.phone", target = "user.phone")
    @Mapping(source = "user.userDetails.iconUrl", target = "user.iconUrl")
    RatingResponse toDto(Rating rating);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Rating partialUpdate(RatingResponse ratingResponse, @MappingTarget Rating rating);

    List<RatingResponse> toListOfDto(List<Rating> ratingList);
}