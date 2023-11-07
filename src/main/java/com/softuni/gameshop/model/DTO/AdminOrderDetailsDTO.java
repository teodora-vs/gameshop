package com.softuni.gameshop.model.DTO;

import com.softuni.gameshop.model.UserEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class AdminOrderDetailsDTO {

    private Long id;

    private LocalDate orderDate;

    private String address;

    private String phoneNumber;

    private List<OrderItemDTO> orderItems;

    private BigDecimal totalPrice;

    private UserEntity user;

    public AdminOrderDetailsDTO() {
    }

    public Long getId() {
        return id;
    }

    public AdminOrderDetailsDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public AdminOrderDetailsDTO setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public AdminOrderDetailsDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public AdminOrderDetailsDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public AdminOrderDetailsDTO setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
        return this;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public AdminOrderDetailsDTO setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public AdminOrderDetailsDTO setUser(UserEntity user) {
        this.user = user;
        return this;
    }
}
