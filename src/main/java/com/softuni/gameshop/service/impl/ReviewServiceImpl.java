package com.softuni.gameshop.service.impl;

import com.softuni.gameshop.model.DTO.AddReviewDTO;
import com.softuni.gameshop.model.Game;
import com.softuni.gameshop.model.Review;
import com.softuni.gameshop.model.UserEntity;
import com.softuni.gameshop.repository.GameRepository;
import com.softuni.gameshop.repository.ReviewRepository;
import com.softuni.gameshop.repository.UserRepository;
import com.softuni.gameshop.service.ReviewService;
import com.softuni.gameshop.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(GameRepository gameRepository, UserRepository userRepository, ModelMapper modelMapper, ReviewRepository reviewRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review createReview(AddReviewDTO addReviewDTO) {
        Optional<Game> optGame = this.gameRepository.findById(addReviewDTO.getGameId());
        if (optGame.isEmpty()){
            throw new ObjectNotFoundException("game not found");
        }

        UserEntity currentUser = this.getCurrentUser();

        Review review = modelMapper.map(addReviewDTO, Review.class);
        review.setStars(addReviewDTO.getStars());
        review.setGame(optGame.get());
        review.setAuthor(currentUser);
        review.setCreated(LocalDateTime.now());

       return this.reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReviewsForGame(Long gameId){
        return this.reviewRepository.findAllByGameId(gameId);
    }

    public UserEntity getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        return this.userRepository.findByUsername(currentUsername).get();
    }

}
