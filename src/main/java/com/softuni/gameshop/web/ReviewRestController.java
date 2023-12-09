package com.softuni.gameshop.web;

import com.softuni.gameshop.model.DTO.ReviewDTO;
import com.softuni.gameshop.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ReviewRestController {

    private final ReviewService reviewService;

    public ReviewRestController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/api/{gameId}/reviews")
    public ResponseEntity<List<ReviewDTO>> getGameReviews(@PathVariable("gameId") Long gameId) {
        List <ReviewDTO> reviews = reviewService.getAllReviewsForGame(gameId)
                .stream().map(r -> new ReviewDTO().setId(r.getId()).setAuthor(r.getAuthor().getFullName())
                        .setStars(r.getStars()).setTextContent(r.getTextContent())
                        .setCreated(r.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))))
                .collect(Collectors.toList());

        return ResponseEntity.ok(reviews);
    }

}
