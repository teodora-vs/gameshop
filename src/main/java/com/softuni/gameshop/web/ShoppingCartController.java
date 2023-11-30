package com.softuni.gameshop.web;

import com.softuni.gameshop.model.DTO.CartItemDTO;
import com.softuni.gameshop.model.Game;
import com.softuni.gameshop.repository.GameRepository;
import com.softuni.gameshop.service.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final GameRepository gameRepository;

    public ShoppingCartController(ShoppingCartService shoppingCartService, GameRepository gameRepository) {
        this.shoppingCartService = shoppingCartService;
        this.gameRepository = gameRepository;
    }

    @GetMapping("/add-to-cart/{id}")
    public String addToCart(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        shoppingCartService.addToCart(id);

        Optional<Game> optGame = gameRepository.findById(id);
        redirectAttributes.addFlashAttribute("itemAddedToCartMessage" ,
                optGame.get().getTitle() + " was successfully added to your cart!");

        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        List<CartItemDTO> cartItems = shoppingCartService.getCartItems();
        BigDecimal totalPrice = shoppingCartService.getCartTotalPrice();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        return "cart";
    }

    @PostMapping("/cart/remove/{id}")
    public String deleteCartItem(@PathVariable Long id) {
        shoppingCartService.removeFromCart(id);

        return "redirect:/cart";
    }
    
}
