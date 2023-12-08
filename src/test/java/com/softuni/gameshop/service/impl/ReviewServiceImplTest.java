package com.softuni.gameshop.service.impl;


import com.softuni.gameshop.model.DTO.AddReviewDTO;
import com.softuni.gameshop.model.Game;
import com.softuni.gameshop.model.Review;
import com.softuni.gameshop.model.UserEntity;
import com.softuni.gameshop.repository.GameRepository;
import com.softuni.gameshop.repository.ReviewRepository;
import com.softuni.gameshop.repository.UserRepository;
import com.softuni.gameshop.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceImplTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ReviewServiceImpl reviewService;


    @Test
    void testCreateReview() {
        AddReviewDTO addReviewDTO = new AddReviewDTO();
        addReviewDTO.setGameId(1L);
        addReviewDTO.setStars(5);

        Game game = new Game();
        game.setId(1L);

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn("testUser");
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
        UserEntity currentUser = new UserEntity();
        currentUser.setUsername("testUser");

        Review review = new Review();
        review.setStars(addReviewDTO.getStars());
        review.setGame(game);
        review.setAuthor(currentUser);
        review.setCreated(LocalDateTime.now());

        when(gameRepository.findById(addReviewDTO.getGameId())).thenReturn(Optional.of(game));
        when(modelMapper.map(addReviewDTO, Review.class)).thenReturn(review);
        when(userRepository.findByUsername(currentUser.getUsername())).thenReturn(Optional.of(currentUser));
        when(reviewRepository.save(ArgumentMatchers.any(Review.class))).thenReturn(review);

        Review createdReview = reviewService.createReview(addReviewDTO);

        assertNotNull(createdReview);
        assertEquals(addReviewDTO.getStars(), createdReview.getStars());
        assertEquals(game, createdReview.getGame());
        assertEquals(currentUser, createdReview.getAuthor());
        assertNotNull(createdReview.getCreated());
        verify(reviewRepository, times(1)).save(ArgumentMatchers.any(Review.class));
    }

    @Test
    void testGetAllReviewsForGame() {
        Long gameId = 1L;
        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review());
        reviews.add(new Review());

        when(reviewRepository.findAllByGameIdOrderByMostRecent(gameId)).thenReturn(reviews);

        List<Review> retrievedReviews = reviewService.getAllReviewsForGame(gameId);

        assertNotNull(retrievedReviews);
        assertEquals(reviews.size(), retrievedReviews.size());
    }

    @Test
    void testCreateReviewGameNotFound() {
        AddReviewDTO addReviewDTO = new AddReviewDTO();
        addReviewDTO.setGameId(1L);

        when(gameRepository.findById(addReviewDTO.getGameId())).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> reviewService.createReview(addReviewDTO));
        verify(reviewRepository, never()).save(ArgumentMatchers.any(Review.class));
    }
}
