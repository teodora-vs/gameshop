package com.softuni.gameshop.model.DTO.order;

import com.softuni.gameshop.model.DTO.CartItemDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDetailsDTO {

    private Long id;

    private LocalDateTime orderDateTime;

    private String address;

    private List<OrderItemDTO> orderItems;

    private BigDecimal totalPrice;

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public OrderDetailsDTO setTotalPrice(BigDecimal totalPrice) {
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

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public OrderDetailsDTO setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
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
