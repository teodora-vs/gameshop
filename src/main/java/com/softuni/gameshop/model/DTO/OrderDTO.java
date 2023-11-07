package com.softuni.gameshop.model.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class OrderDTO {

    @NotBlank(message = "Address is required!")
    private String address;

    @NotBlank
    @Pattern(regexp = "^\\+359\\d{9,}$", message = "Invalid phone number format")
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
