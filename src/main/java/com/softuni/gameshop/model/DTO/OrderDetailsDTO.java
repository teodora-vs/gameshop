package com.softuni.gameshop.model.DTO;

import java.time.LocalDate;
import java.util.List;

public class OrderDetailsDTO {

    private Long id;

    private LocalDate orderDate;

    private String address;

    private List<OrderItemDTO> orderItems;

    private Double totalPrice;

    public Double getTotalPrice() {
        return totalPrice;
    }

    public OrderDetailsDTO setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public OrderDetailsDTO() {
    }

    public Long getId() {
        return id;
    }

    public OrderDetailsDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public OrderDetailsDTO setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public OrderDetailsDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public OrderDetailsDTO setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
        return this;
    }
}
