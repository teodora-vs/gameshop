package com.softuni.gameshop.service.impl;


import com.softuni.gameshop.model.Game;
import com.softuni.gameshop.model.UserEntity;
import com.softuni.gameshop.model.UserRole;
import com.softuni.gameshop.model.enums.UserRoleEnum;
import com.softuni.gameshop.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameShopUserDetailsServiceTest {

    private GameShopUserDetailsService serviceToTest;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp(){
        serviceToTest = new GameShopUserDetailsService(
                mockUserRepository
        );
    }

    @Test
    void testUserNotFound() {
        assertThrows(
                UsernameNotFoundException.class,
                () -> serviceToTest.loadUserByUsername("username")
        );
    }

    @Test
    void testUserFoundException() {
        UserEntity testUserEntity = createTestUser();
        when(mockUserRepository.findByUsername(testUserEntity.getUsername()))
                .thenReturn(Optional.of(testUserEntity));

        UserDetails userDetails =
                serviceToTest.loadUserByUsername(testUserEntity.getUsername());

        assertNotNull(userDetails);

        assertEquals(
                testUserEntity.getUsername(),
                userDetails.getUsername(),
                "Username is not mapped");

        assertEquals(testUserEntity.getPassword(), userDetails.getPassword());
        assertEquals(2, userDetails.getAuthorities().size());
        assertTrue(
                containsAuthority(userDetails, "ROLE_" + UserRoleEnum.ADMIN),
                "The user is not admin");
        assertTrue(
                containsAuthority(userDetails, "ROLE_" + UserRoleEnum.USER),
                "The user is not user");
    }

    private boolean containsAuthority(UserDetails userDetails, String expectedAuthority) {
        return userDetails
                .getAuthorities()
                .stream()
                .anyMatch(a -> expectedAuthority.equals(a.getAuthority()));
    }

    private static UserEntity createTestUser() {
        return new UserEntity()
                .setFullName("Full Name")
                .setUsername("username")
                .setPassword("topsecret")
                .setUserRoles(List.of(
                        new UserRole().setRoleName(UserRoleEnum.ADMIN),
                        new UserRole().setRoleName(UserRoleEnum.USER)
                ));
    }
}
