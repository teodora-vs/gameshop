package com.softuni.gameshop.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime orderDateTime;

    @ManyToOne
    private UserEntity user;

    @OneToMany(fetch = FetchType.EAGER)
    private List<CartItem> cartItems;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false)
    private String phoneNumber;

    public Order() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Order setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Order setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public Order setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public Order setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public Order setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Order setAddress(String address) {
        this.address = address;
        return this;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public Order setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

}
