package com.softuni.gameshop.model.DTO;

import jakarta.validation.constraints.Email;

public class AddEmailDTO {


    @Email
    private String email;

    public AddEmailDTO() {
    }

    public String getEmail() {
        return email;
    }

    public AddEmailDTO setEmail(String email) {
        this.email = email;
        return this;
    }
}
