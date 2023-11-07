package com.softuni.gameshop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import org.springframework.security.core.userdetails.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate orderDate;

    @ManyToOne
    private UserEntity user;

    @OneToMany(fetch = FetchType.EAGER)
    private List<CartItem> cartItems;

    @Column(nullable = false)
    private String address;

    @Column
    private BigDecimal totalPrice;

    @Column
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

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public Order setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
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
