package com.softuni.gameshop.model.DTO.order;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MyOrdersDTO {

    private Long id;

    private LocalDateTime orderDateTime;

    private String address;

    public MyOrdersDTO() {
    }

    public Long getId() {
        return id;
    }

    public MyOrdersDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public MyOrdersDTO setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public MyOrdersDTO setAddress(String address) {
        this.address = address;
        return this;
    }
}
