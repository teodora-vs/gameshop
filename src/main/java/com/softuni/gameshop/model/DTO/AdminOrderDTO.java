package com.softuni.gameshop.model.DTO;

import com.softuni.gameshop.model.UserEntity;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDate;

public class AdminOrderDTO {

    private Long id;

    private LocalDate orderDate;

    private UserEntity user;

    private String address;

    private String phoneNumber;

    public AdminOrderDTO() {
    }

    public Long getId() {
        return id;
    }

    public AdminOrderDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public AdminOrderDTO setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public AdminOrderDTO setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public AdminOrderDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public AdminOrderDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}
