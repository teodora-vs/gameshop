package com.softuni.gameshop.model;

import jakarta.persistence.*;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String gameTitle;

    @Column
    private Integer quantity;

    @Column
    private Double price;

    public OrderItem() {
    }

    public Long getId() {
        return id;
    }

    public OrderItem setId(Long id) {
        this.id = id;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public OrderItem setPrice(Double price) {
        this.price = price;
        return this;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public OrderItem setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public OrderItem setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Double total(){
        return
                this.price * this.quantity;
    }
}
