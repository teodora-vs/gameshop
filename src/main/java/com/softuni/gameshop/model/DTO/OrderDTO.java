package com.softuni.gameshop.model.DTO;

import jakarta.validation.constraints.NotBlank;

public class OrderDTO {

    @NotBlank(message = "Address is required!")
    private String address;

    public OrderDTO() {
    }

    public String getAddress() {
        return address;
    }

    public OrderDTO setAddress(String address) {
        this.address = address;
        return this;
    }
}
