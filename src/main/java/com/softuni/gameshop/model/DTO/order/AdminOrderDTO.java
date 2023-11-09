package com.softuni.gameshop.model.DTO.order;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AdminOrderDTO {

    private Long id;

    private LocalDateTime orderDateTime;

    private String receiver;

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

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public AdminOrderDTO setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
        return this;
    }

    public String getReceiver() {
        return receiver;
    }

    public AdminOrderDTO setReceiver(String receiver) {
        this.receiver = receiver;
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
