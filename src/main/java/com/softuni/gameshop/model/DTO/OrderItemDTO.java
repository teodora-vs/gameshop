package com.softuni.gameshop.model.DTO;

import com.softuni.gameshop.model.Game;

public class OrderItemDTO {

    private Game game;
    private int quantity;
    private Double price; // The price per item
    private Double total; // Total price for this item

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

    public Double getPrice() {
        return price;
    }

    public OrderItemDTO setPrice(Double price) {
        this.price = price;
        return this;
    }

    public Double getTotal() {
        return total;
    }

    public OrderItemDTO setTotal(Double total) {
        this.total = total;
        return this;
    }
}
