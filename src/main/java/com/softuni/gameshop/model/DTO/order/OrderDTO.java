package com.softuni.gameshop.model.DTO.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class OrderDTO {

    @NotBlank(message = "Address is required!")
    private String address;

    @NotBlank
    @Pattern(regexp = "^[0-9]{9}$", message = "Invalid phone number format")
    private String phoneNumber;

    public OrderDTO() {
    }

    public String getAddress() {
        return address;
    }

    public OrderDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public OrderDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}
