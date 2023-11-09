package com.softuni.gameshop.model.DTO.order;

import java.time.LocalDate;

public class MyOrdersDTO {

    private Long id;

    private LocalDate orderDate;

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

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public MyOrdersDTO setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
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
