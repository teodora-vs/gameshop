package com.softuni.gameshop.model.DTO.order;

import com.softuni.gameshop.model.Game;

import java.math.BigDecimal;

public class OrderItemDTO {

    private Game game;
    private int quantity;
    private BigDecimal price; // The price per item
    private BigDecimal total; // Total price for this item

    public OrderItemDTO() {
    }

    public Game getGame() {
        return game;
    }

    public OrderItemDTO setGame(Game game) {
        this.game = game;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public OrderItemDTO setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OrderItemDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public OrderItemDTO setTotal(BigDecimal total) {
        this.total = total;
        return this;
    }
}
