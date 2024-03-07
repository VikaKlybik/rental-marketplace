package com.bsuir.repository;

import com.bsuir.entity.GeolocationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GeolocationDataRepository extends JpaRepository<GeolocationData, Long> {
    @Query("SELECT DISTINCT e.city FROM GeolocationData e WHERE e.property.id IS NOT NULL")
    List<String> findDistinctCities();
    @Query("SELECT DISTINCT e.country FROM GeolocationData e WHERE e.property.id IS NOT NULL")
    List<String> findDistinctCounties();

}