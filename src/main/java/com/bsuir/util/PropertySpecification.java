package com.bsuir.util;

import com.bsuir.dto.PropertyFilterParameter;
import com.bsuir.entity.GeolocationData;
import com.bsuir.entity.Property;
import com.bsuir.enums.PropertyStatus;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class PropertySpecification {
    public static Specification<Property> createSpecificationByPropertyFilterParameter(PropertyFilterParameter propertyFilterParameter, String search) {
        Specification<Property> propertySpecification = isAvailable();

        if(search != null && !search.isEmpty()) {
            propertySpecification = propertySpecification.and(bySearch(search));
        }
        if (propertyFilterParameter.getCountry() != null && !propertyFilterParameter.getCountry().isEmpty()) {
            propertySpecification = propertySpecification.and(byCountry(propertyFilterParameter.getCountry()));
        }
        if (propertyFilterParameter.getCity() != null && !propertyFilterParameter.getCity().isEmpty()) {
            propertySpecification = propertySpecification.and(byCity(propertyFilterParameter.getCity()));
        }
        if (propertyFilterParameter.getPropertyCategoryId() != null) {
            propertySpecification = propertySpecification.and(byPropertyCategory(propertyFilterParameter.getPropertyCategoryId()));
        }
        if (propertyFilterParameter.getEndPrice() != null) {
            propertySpecification = propertySpecification.and(byMaxPrice(propertyFilterParameter.getEndPrice()));
        }
        if (propertyFilterParameter.getStartPrice() != null) {
            propertySpecification = propertySpecification.and(byMinPrice(propertyFilterParameter.getStartPrice()));
        }

        return propertySpecification;
    }

    private static Specification<Property> bySearch(String search) {
        String convertedSearch = ("%" + search + "%").toLowerCase();
        return (root, query, criteriaBuilder) -> {
            Join<Property, GeolocationData> geolocationDataJoin = root.join("geolocationData");
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(geolocationDataJoin.get("country")), convertedSearch),
                    criteriaBuilder.like(criteriaBuilder.lower(geolocationDataJoin.get("city")), convertedSearch),
                    criteriaBuilder.like(criteriaBuilder.lower(geolocationDataJoin.get("address")), convertedSearch),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), convertedSearch)
            );
        };
    }

    private static Specification<Property> byCountry(String county) {
        return (root, query, criteriaBuilder) -> {
            Join<Property, GeolocationData> geolocationDataJoin = root.join("geolocationData");
            return criteriaBuilder.equal(geolocationDataJoin.get("country"), county);
        };
    }

    private static Specification<Property> byCity(String city) {
        return (root, query, criteriaBuilder) -> {
            Join<Property, GeolocationData> geolocationDataJoin = root.join("geolocationData");
            return criteriaBuilder.equal(geolocationDataJoin.get("city"), city);
        };
    }
    private static Specification<Property> byPropertyCategory(Long propertyCategoryId) {
        return (root, query, criteriaBuilder) -> {
            Join<Property, GeolocationData> geolocationDataJoin = root.join("propertyCategory");
            return criteriaBuilder.equal(geolocationDataJoin.get("id"), propertyCategoryId);
        };
    }
    private static Specification<Property> byMinPrice(BigDecimal minPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    private static Specification<Property> byMaxPrice(BigDecimal maxPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    private static Specification<Property> isAvailable() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                root.get("propertyStatus"),
                PropertyStatus.AVAILABLE
        );
    }
}