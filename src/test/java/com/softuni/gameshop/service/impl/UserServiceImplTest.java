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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Test
    void testRegisterUserSuccessfully() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("testUser");
        userRegisterDTO.setPassword("test");
        userRegisterDTO.setConfirmPassword("test");

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUser");

        when(userRepository.findByUsername(userRegisterDTO.getUsername())).thenReturn(Optional.empty());
        when(modelMapper.map(userRegisterDTO, UserEntity.class)).thenReturn(userEntity);
        when(userRoleRepository.findByRoleName(UserRoleEnum.USER)).thenReturn(new UserRole());

        boolean isRegistered = userService.register(userRegisterDTO);

        assertTrue(isRegistered);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testRegisterUserWithExistingUsername() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("existingUser");
        userRegisterDTO.setPassword("password");
        userRegisterDTO.setConfirmPassword("password");

        UserEntity existingUser = new UserEntity();
        existingUser.setUsername("existingUser");

        when(userRepository.findByUsername(userRegisterDTO.getUsername())).thenReturn(Optional.of(existingUser));

        boolean isRegistered = userService.register(userRegisterDTO);

        assertFalse(isRegistered);
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void testAddAdminByUsernameSuccessfully() {
        String username = "testUser";
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setUserRoles(new ArrayList<>());

        UserRole adminRole = new UserRole();
        adminRole.setRoleName(UserRoleEnum.ADMIN);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(userRoleRepository.findByRoleName(UserRoleEnum.ADMIN)).thenReturn(adminRole);

        boolean isAdminAdded = userService.addAdminByUsername(username);

        assertTrue(isAdminAdded);
        assertTrue(userEntity.getUserRoles().contains(adminRole));
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testAddAdminByUsernameUserNotFound() {
        String username = "nonexistentUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        boolean isAdminAdded = userService.addAdminByUsername(username);

        assertFalse(isAdminAdded);
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void testAddAdminByUsernameAdminRoleAlreadyExists() {
        String username = "existingAdmin";
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);

        UserRole adminRole = new UserRole();
        adminRole.setRoleName(UserRoleEnum.ADMIN);

        List<UserRole> userRoles = new ArrayList<>();
        userRoles.add(adminRole);
        userEntity.setUserRoles(userRoles);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(userRoleRepository.findByRoleName(UserRoleEnum.ADMIN)).thenReturn(adminRole);

        boolean isAdminAdded = userService.addAdminByUsername(username);

        assertFalse(isAdminAdded);
        verify(userRepository, never()).save(any(UserEntity.class));
    }

}