package com.softuni.gameshop.service.impl;


import com.softuni.gameshop.model.Game;
import com.softuni.gameshop.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void

}
