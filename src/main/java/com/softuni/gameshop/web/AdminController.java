package com.softuni.gameshop.web;

import com.softuni.gameshop.model.DTO.game.AddGameDTO;
import com.softuni.gameshop.model.DTO.game.EditGameDTO;
import com.softuni.gameshop.model.DTO.game.GameDetailsDTO;
import com.softuni.gameshop.model.DTO.order.AdminOrderDTO;
import com.softuni.gameshop.model.DTO.order.AdminOrderDetailsDTO;
import com.softuni.gameshop.model.Game;
import com.softuni.gameshop.model.enums.GenreNamesEnum;
import com.softuni.gameshop.repository.GameRepository;
import com.softuni.gameshop.service.GameService;
import com.softuni.gameshop.service.OrderService;
import com.softuni.gameshop.service.UserService;
import com.softuni.gameshop.service.exception.ObjectNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {

    private final GameService gameService;
    private final OrderService orderService;
    private final UserService userService;
    private GameRepository gameRepository;

    public AdminController(GameService gameService, OrderService orderService, UserService userService, GameRepository gameRepository) {
        this.gameService = gameService;
        this.orderService = orderService;
        this.userService = userService;
        this.gameRepository = gameRepository;
    }

    @ModelAttribute("addGameDTO")
    public AddGameDTO initAddGameDTO() {
        return new AddGameDTO();
    }

    @ModelAttribute("genres")
    public GenreNamesEnum[] genres() {
        return GenreNamesEnum.values();
    }

    @GetMapping("/games/add")
    public String addGame() {
        return "game-add";
    }

    @PostMapping("/games/add")
    public String addGame(@Valid AddGameDTO addGameDTO,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        if (this.gameService.exists(addGameDTO.getTitle())){
            redirectAttributes.addFlashAttribute("addGameDTO", addGameDTO);
            redirectAttributes.addFlashAttribute("gameExists",true);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addGameDTO", bindingResult);
            return "redirect:/games/add";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addGameDTO", addGameDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addGameDTO", bindingResult);
            return "redirect:/games/add";
        }
        this.gameService.addGame(addGameDTO);
        return "redirect:/games";
    }


    @GetMapping("/games/edit/{id}")
    public String getEditGameForm(@PathVariable Long id, Model model) {
        Optional<Game> optionalGame = this.gameRepository.findById(id);
        if (optionalGame.isEmpty()){
            throw new ObjectNotFoundException("Game with id: "+ id + " not found");
        }
        if (optionalGame.get().isDeleted()){
            throw new ObjectNotFoundException("Game with id: "+ id + " not found");
        }
        GameDetailsDTO gameDetailsDTO = this.gameService.getGameDetails(id);

        EditGameDTO editGameDTO = this.gameService.convertToEditGameDTO(gameDetailsDTO);
        editGameDTO.setGenre(gameDetailsDTO.getGenreName());

        model.addAttribute("editGameDTO", editGameDTO);

        return "game-edit";
    }

    @PostMapping("/games/edit/{id}")
    public String editGame(@PathVariable Long id,@ModelAttribute @Valid EditGameDTO editGameDTO,
            BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        String gameTitle = editGameDTO.getTitle();

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editGameDTO", bindingResult);
            return "redirect:/games/edit/{id}";
        }
        if (this.gameService.existsWithSameTitle(id, gameTitle)){
            redirectAttributes.addFlashAttribute("gameExists",true);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editGameDTO", bindingResult);
            return "redirect:/games/edit/{id}";
        }
        this.gameService.editGame(id, editGameDTO);
        return "redirect:/games/{id}";
    }

    @PostMapping("/games/delete/{id}")
    public String deleteGame(@PathVariable Long id) {
        this.gameService.deleteGame(id);
        return "redirect:/games";
    }

    @GetMapping("/orders")
    public String orders(Model model){
        List<AdminOrderDTO> allOrdersForAdmin = this.orderService.getAllOrdersForAdmin();
        model.addAttribute("orders", allOrdersForAdmin);
        return "admin";
    }

    @GetMapping("/orders/{id}")
    public String viewOrderDetails(@PathVariable Long id, Model model) {
        AdminOrderDetailsDTO adminOrderDetailsDTO = orderService.getOrderDetailsForAdmin(id);
        model.addAttribute("orderDetails", adminOrderDetailsDTO);
        return "order-details-admin";
    }

    @GetMapping("/admin/add")
    public String addAdmin() {
        return "admin-add";
    }

    @PostMapping("/admin/add")
    public String addAdmin(@RequestParam String username, RedirectAttributes redirectAttributes) {
        if (this.userService.addAdminByUsername(username)) {
            redirectAttributes.addFlashAttribute("successfullyAdded", true);
        } else {
            redirectAttributes.addFlashAttribute("invalidUsername", true);
        }
        return "redirect:/admin/add";

    }


}
