package com.softuni.gameshop.web;

import com.softuni.gameshop.model.DTO.AddGameDTO;
import com.softuni.gameshop.model.DTO.GameDetailsDTO;
import com.softuni.gameshop.model.enums.GenreNamesEnum;
import com.softuni.gameshop.service.GameService;
import jakarta.validation.Valid;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/games")
@Controller
public class GameController {

    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @ModelAttribute("addGameDTO")
    public AddGameDTO initAddGameDTO() {
        return new AddGameDTO();
    }

    @ModelAttribute("genres")
    public GenreNamesEnum[] genres() {
        return GenreNamesEnum.values();
    }

    @GetMapping("/add")
    public String addGame() {
        return "game-add";
    }

    @PostMapping("/add")
    public String addGame(@Valid AddGameDTO addGameDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addGameDTO", addGameDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addGameDTO", bindingResult);
            return "redirect:/games/add";
        }

        Long id = gameService.addGame(addGameDTO);
        return "redirect:/home";
    }


    @GetMapping("/{id}")
    public String details(@PathVariable("id") Long id, Model model) {

        GameDetailsDTO gameDetailsDTO = gameService.getGameDetails(id);
        model.addAttribute("game", gameDetailsDTO);

        return "details";
    }
}
