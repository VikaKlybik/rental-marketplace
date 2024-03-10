package com.bsuir.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeolocationDataRequest {
    private Double latitude;
    private Double longitude;
    private String address;
    private String country;
    private String city;
}