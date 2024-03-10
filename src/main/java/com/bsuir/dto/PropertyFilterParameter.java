package com.bsuir.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyFilterParameter {
    private String country;
    private String city;
    private BigDecimal startPrice;
    private BigDecimal endPrice;
    private Long propertyCategoryId;
}