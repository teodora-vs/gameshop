package com.softuni.gameshop.web;

import com.softuni.gameshop.model.DTO.game.GameCardDTO;
import com.softuni.gameshop.model.DTO.game.GameDetailsDTO;
import com.softuni.gameshop.model.Game;
import com.softuni.gameshop.model.Genre;
import com.softuni.gameshop.model.enums.GenreNamesEnum;
import com.softuni.gameshop.service.GameService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GameController {

    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/games")
    public String games(Model model,
                       @PageableDefault(
                               size = 3,
                               sort = "releaseYear", direction = Sort.Direction.DESC
                       ) Pageable pageable){

        List<GenreNamesEnum> genres = List.of(GenreNamesEnum.values());
        Page<GameCardDTO> allGames = gameService.getAllGames(pageable);
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
        Page<GameCardDTO> games;

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

        GameDetailsDTO gameDetailsDTO = gameService.getGameDetails(id);
        model.addAttribute("game", gameDetailsDTO);

        return "details";
    }
}
