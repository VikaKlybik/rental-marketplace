package com.bsuir.mapper;

import com.bsuir.dto.GeolocationDataRequest;
import com.bsuir.dto.GeolocationDataResponse;
import com.bsuir.entity.GeolocationData;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-28T22:37:26+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (GraalVM Community)"
)
@Component
public class GeolocationDataMapperImpl implements GeolocationDataMapper {

    @Override
    public GeolocationDataResponse toDto(GeolocationData geolocationData) {
        if ( geolocationData == null ) {
            return null;
        }

        GeolocationDataResponse.GeolocationDataResponseBuilder geolocationDataResponse = GeolocationDataResponse.builder();

        geolocationDataResponse.id( geolocationData.getId() );
        geolocationDataResponse.latitude( geolocationData.getLatitude() );
        geolocationDataResponse.longitude( geolocationData.getLongitude() );
        geolocationDataResponse.address( geolocationData.getAddress() );
        geolocationDataResponse.country( geolocationData.getCountry() );
        geolocationDataResponse.city( geolocationData.getCity() );

        return geolocationDataResponse.build();
    }

    @Override
    public GeolocationData partialUpdate(GeolocationDataResponse geolocationDataResponse, GeolocationData geolocationData) {
        if ( geolocationDataResponse == null ) {
            return geolocationData;
        }

        if ( geolocationDataResponse.getId() != null ) {
            geolocationData.setId( geolocationDataResponse.getId() );
        }
        if ( geolocationDataResponse.getLatitude() != null ) {
            geolocationData.setLatitude( geolocationDataResponse.getLatitude() );
        }
        if ( geolocationDataResponse.getLongitude() != null ) {
            geolocationData.setLongitude( geolocationDataResponse.getLongitude() );
        }
        if ( geolocationDataResponse.getAddress() != null ) {
            geolocationData.setAddress( geolocationDataResponse.getAddress() );
        }
        if ( geolocationDataResponse.getCountry() != null ) {
            geolocationData.setCountry( geolocationDataResponse.getCountry() );
        }
        if ( geolocationDataResponse.getCity() != null ) {
            geolocationData.setCity( geolocationDataResponse.getCity() );
        }

        return geolocationData;
    }

    @Override
    public GeolocationData toGeolocationData(GeolocationDataRequest geolocationDataRequest) {
        if ( geolocationDataRequest == null ) {
            return null;
        }

        GeolocationData.GeolocationDataBuilder geolocationData = GeolocationData.builder();

        geolocationData.latitude( geolocationDataRequest.getLatitude() );
        geolocationData.longitude( geolocationDataRequest.getLongitude() );
        geolocationData.address( geolocationDataRequest.getAddress() );
        geolocationData.country( geolocationDataRequest.getCountry() );
        geolocationData.city( geolocationDataRequest.getCity() );

        return geolocationData.build();
    }
}
