package com.softuni.gameshop.web;

import com.softuni.gameshop.model.DTO.AddReviewDTO;
import com.softuni.gameshop.model.DTO.game.GameCardDTO;
import com.softuni.gameshop.service.GameService;
import com.softuni.gameshop.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameControllerTest {

    @Mock
    private GameService gameService;

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private GameController gameController;

    @Test
    void testAddGamesAndGenresToModel() {
        // Arrange
        Model model = mock(Model.class);
        Pageable pageable = mock(Pageable.class);
        Page<GameCardDTO> games = new PageImpl<>(List.of(new GameCardDTO()));

        when(gameService.getAllGames(pageable)).thenReturn(games);

        // Act
        String viewName = gameController.games(model, pageable);

        // Assert
        verify(model).addAttribute(eq("games"), eq(games));
        verify(model).addAttribute(eq("genres"), any(List.class));
    }


    @Test
    void testAddValidReviewRedirectToGameDetails() {
        // Arrange
        Long gameId = 1L;
        AddReviewDTO addReviewDTO = new AddReviewDTO();
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        when(bindingResult.hasFieldErrors("stars")).thenReturn(false);
        when(bindingResult.hasFieldErrors("textContent")).thenReturn(false);

        // Act
        String viewName = gameController.addReview(gameId, addReviewDTO, bindingResult, redirectAttributes);

        // Assert
        verify(reviewService).createReview(addReviewDTO);
    }

    @Test
    void testAddReviewWithInvalidStarsRedirectWithError() {
        // Arrange
        Long gameId = 1L;
        AddReviewDTO addReviewDTO = new AddReviewDTO();
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        when(bindingResult.hasFieldErrors("stars")).thenReturn(true);

        // Act
        String viewName = gameController.addReview(gameId, addReviewDTO, bindingResult, redirectAttributes);

        // Assert
        verify(redirectAttributes).addFlashAttribute("invalidStars", true);

    }

    @Test
    void testAddReviewWithInvalidTextRedirectWithError() {
        // Arrange
        Long gameId = 1L;
        AddReviewDTO addReviewDTO = new AddReviewDTO();
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        when(bindingResult.hasFieldErrors("stars")).thenReturn(false);
        when(bindingResult.hasFieldErrors("textContent")).thenReturn(true);

        // Act
        String viewName = gameController.addReview(gameId, addReviewDTO, bindingResult, redirectAttributes);

        // Assert
        verify(redirectAttributes).addFlashAttribute("invalidText", true);
    }
}
