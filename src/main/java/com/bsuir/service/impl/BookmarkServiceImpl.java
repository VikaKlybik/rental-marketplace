package com.bsuir.service.impl;

import com.bsuir.dto.PropertyResponse;
import com.bsuir.entity.Bookmark;
import com.bsuir.entity.Property;
import com.bsuir.exception.BookmarkNotFoundException;
import com.bsuir.exception.PropertyNotFoundException;
import com.bsuir.mapper.PropertyMapper;
import com.bsuir.repository.BookmarkRepository;
import com.bsuir.service.BookmarkService;
import com.bsuir.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final PropertyService propertyService;
    private final PropertyMapper propertyMapper;

    @Override
    public List<PropertyResponse> getBookmarkUser(String username) {
        Bookmark bookmark = bookmarkRepository.findByUserUsername(username)
                .orElseThrow(BookmarkNotFoundException::new);
        return propertyMapper.toListOfDto(
                bookmark.getProperty()
        );
    }

    @Override
    @Transactional
    public void deleteBookmarkProperty(String username, Long propertyId) {
        Bookmark bookmark = bookmarkRepository.findByUserUsername(username)
                .orElseThrow(BookmarkNotFoundException::new);
        List<Property> properties = bookmark.getProperty().stream()
                .filter((property -> !Objects.equals(property.getId(), propertyId)))
                .toList();
        bookmark.setProperty(properties);
    }

    @Override
    @Transactional
    public void addBookmarkProperty(String username, Long propertyId) {
        if (!propertyService.isPropertyExist(propertyId)) {
            throw new PropertyNotFoundException();
        }

        Bookmark bookmark = bookmarkRepository.findByUserUsername(username)
                .orElseThrow(BookmarkNotFoundException::new);

        Property property = new Property();
        property.setId(propertyId);
        bookmark.getProperty().add(property);
    }

    @Override
    public Integer bookmarkSize(String username) {
        return getBookmarkUser(username)
                .size();
    }
}