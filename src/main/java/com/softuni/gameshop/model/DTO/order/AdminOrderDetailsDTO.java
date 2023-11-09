package com.softuni.gameshop.model.DTO.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AdminOrderDetailsDTO {

    private Long id;

    private LocalDateTime orderDateTime;

    private String address;

    private String phoneNumber;

    private List<OrderItemDTO> orderItems;

    private BigDecimal totalPrice;

    private String receiver;

    public AdminOrderDetailsDTO() {
    }

    public Long getId() {
        return id;
    }

    public AdminOrderDetailsDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public AdminOrderDetailsDTO setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
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

    public String getReceiver() {
        return receiver;
    }

    public AdminOrderDetailsDTO setReceiver(String receiver) {
        this.receiver = receiver;
        return this;
    }
}
