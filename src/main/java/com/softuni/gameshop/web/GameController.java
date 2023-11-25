package com.softuni.gameshop.web;

import com.softuni.gameshop.model.DTO.AddReviewDTO;
import com.softuni.gameshop.model.DTO.game.GameSummaryDTO;
import com.softuni.gameshop.model.DTO.game.GameDetailsDTO;
import com.softuni.gameshop.model.UserEntity;
import com.softuni.gameshop.model.enums.GenreNamesEnum;
import com.softuni.gameshop.repository.UserRepository;
import com.softuni.gameshop.service.GameService;
import com.softuni.gameshop.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class GameController {

    private GameService gameService;
    private ReviewService reviewService;
    private final UserRepository userRepository;

    public GameController(GameService gameService, ReviewService reviewService, UserRepository userRepository) {
        this.gameService = gameService;
        this.reviewService = reviewService;
        this.userRepository = userRepository;
    }

    @GetMapping("/games")
    public String games(Model model,
                       @PageableDefault(
                               size = 3,
                               sort = "releaseYear", direction = Sort.Direction.DESC
                       ) Pageable pageable){

        List<GenreNamesEnum> genres = List.of(GenreNamesEnum.values());
        Page<GameSummaryDTO> allGames = gameService.getAllGames(pageable);
        model.addAttribute("games", allGames);
        model.addAttribute("genres", genres);

        return "games";
    }

    @GetMapping("/games/byGenre")
    public String getByGenre(@RequestParam(value = "genre", required = false) GenreNamesEnum selectedGenre,
                             Model model,
                             @PageableDefault(
                                     size = 3,
                                     sort = "releaseYear", direction = Sort.Direction.DESC
                             ) Pageable pageable){

        List<GenreNamesEnum> genres = List.of(GenreNamesEnum.values());
        Page<GameSummaryDTO> games;

        if (selectedGenre != null) {
            games = gameService.getGamesByGenre(selectedGenre, pageable);
        } else {
            games = gameService.getAllGames(pageable);
        }

        model.addAttribute("genres", genres);
        model.addAttribute("selectedGenre", selectedGenre);
        model.addAttribute("games", games);

        return "games";
    }

    @GetMapping("/games/{id}")
    public String details(@PathVariable("id") Long id, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()){
            String currentUsername = authentication.getName();
            Optional<UserEntity> optionalUser = this.userRepository.findByUsername(currentUsername);

            if (optionalUser.isPresent() && optionalUser.get().getEmail() == null){
                model.addAttribute("confirmed", false);
            }
        }

        GameDetailsDTO gameDetailsDTO = gameService.getGameDetails(id);
        Double averageScore = gameService.getAverageScore(id);
        model.addAttribute("game", gameDetailsDTO);
        model.addAttribute("averageScore", averageScore);

        return "details";
    }

    @PostMapping("/games/{gameId}/add-review")
    public String addReview(@PathVariable("gameId") Long gameId, @Valid AddReviewDTO addReviewDTO
    , BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasFieldErrors("stars")){
            redirectAttributes.addFlashAttribute("invalidStars", true);
            return "redirect:/games/{gameId}";
        }
        if (bindingResult.hasFieldErrors("textContent")){
            redirectAttributes.addFlashAttribute("invalidText", true);
            return "redirect:/games/{gameId}";
        }
        this.reviewService.createReview(addReviewDTO);

        return "redirect:/games/{gameId}";
    }


}
