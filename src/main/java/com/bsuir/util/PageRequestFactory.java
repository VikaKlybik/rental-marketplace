package com.bsuir.util;

import com.bsuir.constant.RentalPropertiesConstants;
import com.bsuir.entity.PageSetting;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Objects;

public class PageRequestFactory {
    public static PageRequest buildPageRequest(PageSetting pageSetting) {
        Sort sort = buildSort(pageSetting);
        return PageRequest.of(pageSetting.getPageNumber(), pageSetting.getElementsPerPage(), sort);
    }

    private static Sort buildSort(PageSetting pageSetting) {
        if (Objects.isNull(pageSetting.getSortField())) {
            return Sort.unsorted();
        }

        Sort sort = Sort.by(pageSetting.getSortField());

        if (pageSetting.getDirection().equals(RentalPropertiesConstants.DefaultValue.SORT_ASC)) {
            return sort.ascending();
        }
        return sort.descending();
    }
}