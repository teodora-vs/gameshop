package com.softuni.gameshop.web;

import com.softuni.gameshop.model.DTO.game.GameCardDTO;
import com.softuni.gameshop.model.DTO.game.GameDetailsDTO;
import com.softuni.gameshop.service.GameService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GameController {

    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/games")
    public String home(Model model,
                       @PageableDefault(
                               size = 3,
                               sort = "releaseYear", direction = Sort.Direction.DESC
                       ) Pageable pageable){

        Page<GameCardDTO> allGames = gameService.getAllGames(pageable);
        model.addAttribute("games", allGames);

        return "games";
    }


    @GetMapping("/games/{id}")
    public String details(@PathVariable("id") Long id, Model model) {

        GameDetailsDTO gameDetailsDTO = gameService.getGameDetails(id);
        model.addAttribute("game", gameDetailsDTO);

        return "details";
    }
}
