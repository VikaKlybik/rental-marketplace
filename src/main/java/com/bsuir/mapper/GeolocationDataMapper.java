package com.bsuir.mapper;

import com.bsuir.dto.GeolocationDataRequest;
import com.bsuir.dto.GeolocationDataResponse;
import com.bsuir.entity.GeolocationData;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface GeolocationDataMapper {

    GeolocationDataResponse toDto(GeolocationData geolocationData);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GeolocationData partialUpdate(GeolocationDataResponse geolocationDataResponse, @MappingTarget GeolocationData geolocationData);

    GeolocationData toGeolocationData(GeolocationDataRequest geolocationDataRequest);
}