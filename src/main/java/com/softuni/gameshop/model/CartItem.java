package com.softuni.gameshop.model;

import jakarta.persistence.*;

@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Game game;

    @Column
    private Integer quantity;


    public CartItem() {
    }

    public Long getId() {
        return id;
    }

    public CartItem setId(Long id) {
        this.id = id;
        return this;
    }

    public Game getGame() {
        return game;
    }

    public CartItem setGame(Game game) {
        this.game = game;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public CartItem setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public double getTotal(){
        return this.game.getPrice() * quantity;
    }
}
