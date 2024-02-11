package com.softuni.gameshop.service.impl;

import static org.junit.jupiter.api.Assertions.*;


import com.softuni.gameshop.model.CartItem;
import com.softuni.gameshop.model.DTO.UserRegisterDTO;
import com.softuni.gameshop.model.ShoppingCart;
import com.softuni.gameshop.model.UserEntity;
import com.softuni.gameshop.model.UserRole;
import com.softuni.gameshop.model.enums.UserRoleEnum;
import com.softuni.gameshop.repository.ShoppingCartRepository;
import com.softuni.gameshop.repository.UserRepository;
import com.softuni.gameshop.repository.UserRoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

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
    void testAddAdminByUsernameAdminRoleAlreadyExists() {
        String username = "existingAdmin";
        UserEntity adminUser = new UserEntity();
        adminUser.setUsername(username);
        UserRole adminRole = new UserRole();
        adminRole.setRoleName(UserRoleEnum.ADMIN);
        adminUser.setUserRoles(List.of(adminRole));

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(adminUser));
        when(userRoleRepository.findByRoleName(UserRoleEnum.ADMIN)).thenReturn(adminRole);

        boolean isAdminAdded = userService.addAdminByUsername(username);

        assertFalse(isAdminAdded);
        assertEquals(1, adminUser.getUserRoles().size()); // Ensure existing admin roles are not modified
        verify(userRepository, never()).save(any(UserEntity.class));
        verify(shoppingCartRepository, never()).deleteById(anyLong());
        verify(userRepository, never()).flush();
    }

    @Test
    void testAddAdminByUsernameWithNullEmail() {
        String username = "userWithNullEmail";
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setEmail(null);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));

        boolean isAdminAdded = userService.addAdminByUsername(username);

        assertFalse(isAdminAdded);
        verify(userRepository, never()).save(any(UserEntity.class));
        verify(shoppingCartRepository, never()).deleteById(anyLong());
        verify(userRepository, never()).flush();
    }

    @Test
    void testAddAdminByUsernameUserNotFound() {
        String username = "nonexistentUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        boolean isAdminAdded = userService.addAdminByUsername(username);

        assertFalse(isAdminAdded);
        verify(userRepository, never()).save(any(UserEntity.class));
        verify(shoppingCartRepository, never()).deleteById(anyLong());
        verify(userRepository, never()).flush();
    }

    @Test
    void testAddAdminByUsernameSuccess() {
        String username = "userToPromote";
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setEmail("test@example.com");
        UserRole userRole = new UserRole();
        userRole.setRoleName(UserRoleEnum.USER);
        userEntity.setUserRoles(new ArrayList<>(List.of(userRole)));

        ShoppingCart shoppingCart = new ShoppingCart().setId(1L);
        CartItem cartItem = new CartItem();

        shoppingCart.setCartItems(new ArrayList<>(List.of(cartItem)));
        userEntity.setShoppingCart(shoppingCart);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(userRoleRepository.findByRoleName(UserRoleEnum.ADMIN)).thenReturn(new UserRole());

        boolean isAdminAdded = userService.addAdminByUsername(username);

        assertTrue(isAdminAdded);
        assertNull(userEntity.getShoppingCart());
        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(shoppingCartRepository, times(1)).deleteById(anyLong());
        verify(userRepository, times(1)).flush();
    }

}