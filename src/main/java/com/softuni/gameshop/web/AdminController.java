package com.softuni.gameshop.web;

import com.softuni.gameshop.model.DTO.AddGameDTO;
import com.softuni.gameshop.model.enums.GenreNamesEnum;
import com.softuni.gameshop.service.GameService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {

    private GameService gameService;

    public AdminController(GameService gameService) {
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

    @GetMapping("/orders")
    public String admin(){
        return "admin";
    }

    @GetMapping("/games/add")
    public String addGame() {
        return "game-add";
    }

    @PostMapping("/games/add")
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

    @PostMapping("/games/delete/{id}")
    public String deleteGame(@PathVariable Long id) {
        //TODO : fix
        this.gameService.deleteGame(id);
        return "redirect:/home";
    }

}
