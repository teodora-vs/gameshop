package com.softuni.gameshop.web;

import com.softuni.gameshop.model.DTO.CartItemDTO;
import com.softuni.gameshop.service.ShoppingCartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class ShoppingCartController {

    private ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/add-to-cart/{id}")
    public String addToCart(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        shoppingCartService.addToCart(id);

        session.setAttribute("itemAddedToCart", true);

        redirectAttributes.addFlashAttribute("itemAddedToCartMessage", "Item successfully added to the cart!");

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
