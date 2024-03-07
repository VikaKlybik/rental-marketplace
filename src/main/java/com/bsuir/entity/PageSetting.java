package com.bsuir.entity;

import com.bsuir.constant.RentalPropertiesConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageSetting {
    private int pageNumber = RentalPropertiesConstants.DefaultValue.PAGE;
    private int elementsPerPage = RentalPropertiesConstants.DefaultValue.ELEMENT_PER_PAGE;
    private String direction = RentalPropertiesConstants.DefaultValue.SORT_DESC;
    private String sortField;
}