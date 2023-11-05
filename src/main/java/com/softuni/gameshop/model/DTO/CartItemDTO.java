package com.softuni.gameshop.model.DTO;

import com.softuni.gameshop.model.Game;
import com.softuni.gameshop.model.ShoppingCart;
import jakarta.persistence.*;

public class CartItemDTO {

    private Long id;

    private Game game;

    private ShoppingCart shoppingCart;

    private Integer quantity;

    public CartItemDTO() {
    }

    public Long getId() {
        return id;
    }

    public CartItemDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public Game getGame() {
        return game;
    }

    public CartItemDTO setGame(Game game) {
        this.game = game;
        return this;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public CartItemDTO setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public CartItemDTO setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public double getTotal(){
        return this.game.getPrice() * quantity;
    }
}
