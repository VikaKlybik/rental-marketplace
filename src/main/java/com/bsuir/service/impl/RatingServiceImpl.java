package com.bsuir.service.impl;

import com.bsuir.dto.RatingRequest;
import com.bsuir.dto.RatingResponse;
import com.bsuir.entity.Rating;
import com.bsuir.entity.User;
import com.bsuir.exception.UserNotFoundException;
import com.bsuir.mapper.RatingMapper;
import com.bsuir.repository.RatingRepository;
import com.bsuir.repository.UserRepository;
import com.bsuir.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;
    private final UserRepository userRepository;

    @Override
    public List<RatingResponse> getAllRatingForUser(Long userId) {
        List<Rating> ratingList = ratingRepository.findAllByUserId(userId);

        return ratingMapper.toListOfDto(ratingList);
    }

    @Override
    public void createRating(RatingRequest ratingRequest, String username) {
        User forUser = new User();
        forUser.setId(ratingRequest.getUserId());

        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UserNotFoundException(username));

        Rating rating = new Rating();
        rating.setRating(ratingRequest.getRating());
        rating.setForUser(forUser);
        rating.setUser(user);
        rating.setComment(ratingRequest.getComment());

        ratingRepository.save(rating);
    }

    @Override
    public void deleteRating(Long ratingId) {
        ratingRepository.deleteById(ratingId);
    }
}