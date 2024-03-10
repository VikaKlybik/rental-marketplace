package com.bsuir.service;

import com.bsuir.dto.PropertyResponse;

import java.util.List;

public interface BookmarkService {
    List<PropertyResponse> getBookmarkUser(String username);
    void deleteBookmarkProperty(String username,Long bookmarkId);
    void addBookmarkProperty(String username, Long propertyId);
    Integer bookmarkSize(String username);
}