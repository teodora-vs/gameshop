package com.softuni.gameshop.web;

import com.softuni.gameshop.model.DTO.AddGameDTO;
import com.softuni.gameshop.model.DTO.AdminOrderDTO;
import com.softuni.gameshop.model.DTO.AdminOrderDetailsDTO;
import com.softuni.gameshop.model.DTO.OrderDetailsDTO;
import com.softuni.gameshop.model.enums.GenreNamesEnum;
import com.softuni.gameshop.service.GameService;
import com.softuni.gameshop.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AdminController {

    private GameService gameService;
    private OrderService orderService;

    public AdminController(GameService gameService, OrderService orderService) {
        this.gameService = gameService;
        this.orderService = orderService;
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

}
