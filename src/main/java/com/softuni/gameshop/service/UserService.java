package com.softuni.gameshop.service;

import com.softuni.gameshop.model.DTO.UserRegisterDTO;
import com.softuni.gameshop.model.UserEntity;

import java.util.Optional;

public interface UserService {

    boolean register(UserRegisterDTO userRegisterDTO);

    Optional<UserEntity> getByUsername(String username);

    void addAdmin();
}
