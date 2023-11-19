package com.softuni.gameshop.service.impl;

import static org.junit.jupiter.api.Assertions.*;


import com.softuni.gameshop.model.DTO.UserRegisterDTO;
import com.softuni.gameshop.model.UserEntity;
import com.softuni.gameshop.model.UserRole;
import com.softuni.gameshop.model.enums.UserRoleEnum;
import com.softuni.gameshop.repository.ShoppingCartRepository;
import com.softuni.gameshop.repository.UserRepository;
import com.softuni.gameshop.repository.UserRoleRepository;
import com.softuni.gameshop.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testRegisterUserSuccessfully() {
        // Arrange
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("testUser");
        userRegisterDTO.setPassword("password");
        userRegisterDTO.setConfirmPassword("password");

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUser");

        when(userRepository.findByUsername(userRegisterDTO.getUsername())).thenReturn(Optional.empty());
        when(modelMapper.map(userRegisterDTO, UserEntity.class)).thenReturn(userEntity);
        when(userRoleRepository.findByRoleName(UserRoleEnum.USER)).thenReturn(new UserRole());

        // Act
        boolean isRegistered = userService.register(userRegisterDTO);

        // Assert
        assertTrue(isRegistered);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testRegisterUserWithExistingUsername() {
        // Arrange
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("existingUser");
        userRegisterDTO.setPassword("password");
        userRegisterDTO.setConfirmPassword("password");

        UserEntity existingUser = new UserEntity();
        existingUser.setUsername("existingUser");

        when(userRepository.findByUsername(userRegisterDTO.getUsername())).thenReturn(Optional.of(existingUser));

        // Act
        boolean isRegistered = userService.register(userRegisterDTO);

        // Assert
        assertFalse(isRegistered);
        verify(userRepository, never()).save(any(UserEntity.class));
    }

}