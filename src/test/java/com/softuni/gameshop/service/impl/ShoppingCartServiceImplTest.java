package com.softuni.gameshop.service.impl;
import com.softuni.gameshop.model.DTO.CartItemDTO;
import com.softuni.gameshop.model.Game;
import com.softuni.gameshop.model.UserEntity;
import com.softuni.gameshop.model.ShoppingCart;
import com.softuni.gameshop.model.CartItem;
import com.softuni.gameshop.repository.GameRepository;
import com.softuni.gameshop.repository.ShoppingCartRepository;
import com.softuni.gameshop.repository.UserRepository;
import com.softuni.gameshop.service.impl.ShoppingCartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class ShoppingCartServiceImplTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRemoveFromCart() {
        // Given
        UserEntity user = new UserEntity();
        ShoppingCart shoppingCart = new ShoppingCart();
        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setQuantity(2);
        cartItem.setGame(new Game());
        shoppingCart.setCartItems(Collections.singletonList(cartItem));
        user.setShoppingCart(shoppingCart);

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(shoppingCartRepository.save(any())).thenReturn(shoppingCart);

        // When
        shoppingCartService.removeFromCart(1L);

        // Then
        assertEquals(1, shoppingCart.getCartItems().size());
        assertEquals(1, shoppingCart.getCartItems().get(0).getQuantity());
        verify(shoppingCartRepository, times(1)).save(shoppingCart);
    }

    @Test
    void testAddToCart() {
        Game game = new Game();
        game.setId(1L);

        UserEntity user = new UserEntity();
        ShoppingCart shoppingCart = new ShoppingCart();
        user.setShoppingCart(shoppingCart);

        when(gameRepository.findById(any())).thenReturn(Optional.of(game));
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(shoppingCartRepository.save(any())).thenReturn(shoppingCart);

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

    @Test
    void testGetCurrentUser() {
        UserEntity user = new UserEntity();
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        UserEntity currentUser = shoppingCartService.getCurrentUser();

        assertEquals(user, currentUser);
    }


}