package com.softuni.gameshop.service;

import com.softuni.gameshop.model.DTO.AddReviewDTO;
import com.softuni.gameshop.model.Review;

import java.util.List;

public interface ReviewService {

    void create( AddReviewDTO addReviewDTO);

    List<Review> getAllReviewsForGame(Long gameId);
}
