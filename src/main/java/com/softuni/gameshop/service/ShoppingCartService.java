package com.softuni.gameshop.service;

import com.softuni.gameshop.model.DTO.CartItemDTO;
import com.softuni.gameshop.model.UserEntity;

import java.math.BigDecimal;
import java.util.List;

public interface ShoppingCartService {
    void removeFromCart(Long id) ;

    void addToCart(Long gameId);

    List<CartItemDTO> getCartItems();

    UserEntity getCurrentUser();

    BigDecimal getCartTotalPrice();

    void clearCart();
}
