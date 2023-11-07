package com.softuni.gameshop.model.DTO;

import com.softuni.gameshop.model.Game;
import com.softuni.gameshop.model.ShoppingCart;
import jakarta.persistence.*;

import java.math.BigDecimal;

public class CartItemDTO {

    private Long id;

    private Game game;

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


    public Integer getQuantity() {
        return quantity;
    }

    public CartItemDTO setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public BigDecimal getTotal(){
        BigDecimal total = this.game.getPrice().multiply(BigDecimal.valueOf(quantity));
        return total;
    }
}
