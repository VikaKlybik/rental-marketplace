package com.bsuir.service;

import com.bsuir.dto.RatingRequest;
import com.bsuir.dto.RatingResponse;

import java.util.List;

public interface RatingService {
    List<RatingResponse> getAllRatingForUser(Long userId);

    void createRating(RatingRequest ratingRequest, String username);

    void deleteRating(Long ratingId);
}