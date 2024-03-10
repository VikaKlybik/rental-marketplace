package com.bsuir.service;

import java.util.List;

public interface GeolocationDataService {
    List<String> getUniqueCountries();
    List<String> getUniqueCities();
}