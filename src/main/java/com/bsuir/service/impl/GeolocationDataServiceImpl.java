package com.bsuir.service.impl;

import com.bsuir.repository.GeolocationDataRepository;
import com.bsuir.service.GeolocationDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeolocationDataServiceImpl implements GeolocationDataService {

    private final GeolocationDataRepository geolocationDataRepository;
    @Override
    public List<String> getUniqueCountries() {
        return geolocationDataRepository.findDistinctCounties();
    }

    @Override
    public List<String> getUniqueCities() {
        return geolocationDataRepository.findDistinctCities();
    }
}