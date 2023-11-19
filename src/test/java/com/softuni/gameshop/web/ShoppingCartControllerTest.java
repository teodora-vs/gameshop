package com.softuni.gameshop.web;

import com.softuni.gameshop.model.DTO.CartItemDTO;
import com.softuni.gameshop.model.Game;
import com.softuni.gameshop.model.ShoppingCart;
import com.softuni.gameshop.repository.ShoppingCartRepository;
import com.softuni.gameshop.service.ShoppingCartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ShoppingCartController.class)
@AutoConfigureMockMvc
class ShoppingCartControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingCartService shoppingCartService;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testAddToCart() throws Exception {

        Long gameId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get("/add-to-cart/{id}", gameId))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"));

        verify(shoppingCartService,times(1)).addToCart(1L);
    }


}