package com.softuni.gameshop.service.impl;
import com.softuni.gameshop.model.*;
import com.softuni.gameshop.model.DTO.CartItemDTO;
import com.softuni.gameshop.model.enums.UserRoleEnum;
import com.softuni.gameshop.repository.GameRepository;
import com.softuni.gameshop.repository.ShoppingCartRepository;
import com.softuni.gameshop.repository.UserRepository;
import com.softuni.gameshop.repository.UserRoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@WithMockUser(username = "testUser")
@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceImplTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @Mock
    private Authentication authentication;



    @BeforeEach
    void setUp() {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @AfterEach
        void teardown(){
        this.shoppingCartRepository.deleteAll();
    }

    @Test
    void testRemoveFromCart() {
        UserEntity user = new UserEntity();
        ShoppingCart shoppingCart = new ShoppingCart();
        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setQuantity(2);
        cartItem.setGame(new Game().setQuantity(2));
        shoppingCart.setCartItems(Collections.singletonList(cartItem));
        user.setShoppingCart(shoppingCart);

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(shoppingCartRepository.save(any())).thenReturn(shoppingCart);

        shoppingCartService.removeFromCart(1L);

        assertEquals(1, shoppingCart.getCartItems().size());
        assertEquals(1, shoppingCart.getCartItems().get(0).getQuantity());
        verify(shoppingCartRepository, times(1)).save(shoppingCart);
    }

    @Test
    @WithMockUser
    void testAddToCart() {
        Game game = new Game();
        game.setId(1L);
        game.setQuantity(1);
        UserRole userRole = new UserRole().setId(1L).setRoleName(UserRoleEnum.USER);
        UserEntity user = new UserEntity();
        user.setUserRoles(Collections.singletonList(userRole));
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCart.setId(1L);
        user.setShoppingCart(shoppingCart);

        when(gameRepository.findById(any())).thenReturn(Optional.of(game));
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user.setUserRoles(Collections.singletonList(userRole))));
        when(shoppingCartRepository.save(any())).thenReturn(shoppingCart);
        when(userRoleRepository.findByRoleName(UserRoleEnum.USER)).thenReturn(userRole);
        shoppingCartService.addToCart(1L);

        assertEquals(1, shoppingCart.getCartItems().size());
        assertEquals(1, shoppingCart.getCartItems().get(0).getQuantity());
        verify(shoppingCartRepository, times(1)).save(shoppingCart);
    }

    @Test
    void testGetCartItems() {
        Game game = new Game();
        game.setDeleted(false);

        UserEntity user = new UserEntity();
        ShoppingCart shoppingCart = new ShoppingCart();
        CartItem cartItem = new CartItem();
        cartItem.setGame(game);
        shoppingCart.setCartItems(Collections.singletonList(cartItem));
        user.setShoppingCart(shoppingCart);

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(modelMapper.map(any(), eq(CartItemDTO.class))).thenReturn(new CartItemDTO());

        List<CartItemDTO> cartItemsDTOs = shoppingCartService.getCartItems();

        assertEquals(1, cartItemsDTOs.size());
    }


}