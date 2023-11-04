package com.softuni.gameshop.service;

import com.softuni.gameshop.model.CartItem;
import com.softuni.gameshop.model.Game;

import java.util.List;

public interface ShoppingCartService {
    void removeFromCart(Long id) ;

    void addToCart(Long gameId);

    List<CartItem> getCartItems();

    double calculateTotalPrice();
}
