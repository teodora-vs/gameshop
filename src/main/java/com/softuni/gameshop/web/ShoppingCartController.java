package com.softuni.gameshop.web;

import com.softuni.gameshop.model.CartItem;
import com.softuni.gameshop.model.Game;
import com.softuni.gameshop.service.ShoppingCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ShoppingCartController {

    private ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/add-to-cart/{id}")
    public String addToCart(@PathVariable Long id) {
        shoppingCartService.addToCart(id);
        return "redirect:/home";
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        List<CartItem> cartItems = shoppingCartService.getCartItems();
        double totalPrice = shoppingCartService.calculateTotalPrice();
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        return "cart";
    }

    @PostMapping("/cart/remove/{id}")
    public String deleteCartItem(@PathVariable Long id) {
        // Implement delete logic here
        shoppingCartService.removeFromCart(id);
        // Redirect to the cart view or other appropriate page
        return "redirect:/cart";
    }


//    @GetMapping("/remove-from-cart/{gameId}")
//    public String removeFromCart(@PathVariable Long gameId) {
//        shoppingCartService.removeFromCart(gameId);
//        return "redirect:/cart";
//    }
}
