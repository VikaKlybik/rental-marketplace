package com.bsuir.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeolocationDataResponse {
    private Long id;
    private Double latitude;
    private Double longitude;
    private String address;
    private String country;
    private String city;
}