package com.softuni.gameshop.web;

import com.softuni.gameshop.model.DTO.AddReviewDTO;
import com.softuni.gameshop.model.DTO.game.GameSummaryDTO;
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
        Model model = mock(Model.class);
        Pageable pageable = mock(Pageable.class);
        Page<GameSummaryDTO> games = new PageImpl<>(List.of(new GameSummaryDTO()));

        when(gameService.getAllGames(pageable)).thenReturn(games);

        String viewName = gameController.games(model, pageable);

        verify(model).addAttribute(eq("games"), eq(games));
        verify(model).addAttribute(eq("genres"), any(List.class));
    }


    @Test
    void testAddValidReviewRedirectToGameDetails() {
        Long gameId = 1L;
        AddReviewDTO addReviewDTO = new AddReviewDTO();
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        when(bindingResult.hasFieldErrors("stars")).thenReturn(false);
        when(bindingResult.hasFieldErrors("textContent")).thenReturn(false);

        String viewName = gameController.addReview(gameId, addReviewDTO, bindingResult, redirectAttributes);

        verify(reviewService).createReview(addReviewDTO);
    }

    @Test
    void testAddReviewWithInvalidStarsRedirectWithError() {
        Long gameId = 1L;
        AddReviewDTO addReviewDTO = new AddReviewDTO();
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        when(bindingResult.hasFieldErrors("stars")).thenReturn(true);

        String viewName = gameController.addReview(gameId, addReviewDTO, bindingResult, redirectAttributes);

        verify(redirectAttributes).addFlashAttribute("invalidStars", true);
    }

    @Test
    void testAddReviewWithInvalidTextRedirectWithError() {
        Long gameId = 1L;
        AddReviewDTO addReviewDTO = new AddReviewDTO();
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        when(bindingResult.hasFieldErrors("stars")).thenReturn(false);
        when(bindingResult.hasFieldErrors("textContent")).thenReturn(true);

        String viewName = gameController.addReview(gameId, addReviewDTO, bindingResult, redirectAttributes);

        verify(redirectAttributes).addFlashAttribute("invalidText", true);
    }
}
