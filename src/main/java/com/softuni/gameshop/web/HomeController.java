package com.softuni.gameshop.web;

import com.softuni.gameshop.model.DTO.GameCardDTO;
import com.softuni.gameshop.service.GameService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private GameService gameService;

    public HomeController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model,
                       @PageableDefault(
                               size = 3,
                               sort = "releaseYear", direction = Sort.Direction.DESC
                       )Pageable pageable){

        Page<GameCardDTO> allGames = gameService.getAllGames(pageable);
        model.addAttribute("games", allGames);

        return "home";
    }



}
