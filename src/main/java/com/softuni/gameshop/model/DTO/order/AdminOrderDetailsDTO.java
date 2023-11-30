package com.softuni.gameshop.model.DTO.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AdminOrderDetailsDTO extends OrderDetailsDTO {

    private String phoneNumber;

    private String receiver;

    private String email;

    public AdminOrderDetailsDTO() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public AdminOrderDetailsDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getReceiver() {
        return receiver;
    }

    public AdminOrderDetailsDTO setReceiver(String receiver) {
        this.receiver = receiver;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AdminOrderDetailsDTO setEmail(String email) {
        this.email = email;
        return this;
    }
}
