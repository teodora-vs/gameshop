package com.softuni.gameshop.service.impl;

import com.softuni.gameshop.model.CartItem;
import com.softuni.gameshop.model.DTO.CartItemDTO;
import com.softuni.gameshop.model.Game;
import com.softuni.gameshop.model.ShoppingCart;
import com.softuni.gameshop.model.UserEntity;
import com.softuni.gameshop.repository.GameRepository;
import com.softuni.gameshop.repository.ShoppingCartRepository;
import com.softuni.gameshop.repository.UserRepository;
import com.softuni.gameshop.service.ShoppingCartService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private ModelMapper modelMapper;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartServiceImpl(GameRepository gameRepository, UserRepository userRepository, ShoppingCartRepository shoppingCartRepository, ModelMapper modelMapper, ShoppingCartRepository shoppingCartRepository1) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.shoppingCartRepository = shoppingCartRepository1;
    }

    @Override
    public void removeFromCart(Long id) {
        UserEntity user = this.getCurrentUser();
        ShoppingCart shoppingCart = user.getShoppingCart();

        if (shoppingCart != null) {
            Optional<CartItem> itemToRemove = shoppingCart.getCartItems().stream()
                    .filter(cartItem -> cartItem.getId().equals(id))
                    .findFirst();

            if (itemToRemove.isPresent()) {
                if (itemToRemove.get().getQuantity() > 1){
                    int currentQuantity = itemToRemove.get().getQuantity();
                    itemToRemove.get().setQuantity(currentQuantity - 1);
                    shoppingCartRepository.save(shoppingCart);
                } else {
                    shoppingCart.getCartItems().remove(itemToRemove.get());
                    shoppingCartRepository.save(shoppingCart);
                }
            }
        }
    }

    @Override
    public void addToCart(Long gameId) {
        Optional<Game> optGame = this.gameRepository.findById(gameId);
        UserEntity user = this.getCurrentUser();
        ShoppingCart shoppingCart = user.getShoppingCart();

        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
            shoppingCart.setUser(user);
            shoppingCartRepository.save(shoppingCart);
        }

        if (shoppingCart.getCartItems() == null){
            shoppingCart.setCartItems(new ArrayList<>());
        }

        for (CartItem cartItem : shoppingCart.getCartItems()) {
            if (cartItem.getGame().getId().equals(optGame.get().getId())) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                shoppingCartRepository.save(shoppingCart);
                return;
            }
        }

        CartItem cartItem = new CartItem();
        cartItem.setGame(optGame.get());
        cartItem.setQuantity(1);
        shoppingCart.getCartItems().add(cartItem);
        shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public List<CartItemDTO> getCartItems() {
        UserEntity user = this.getCurrentUser();
        ShoppingCart shoppingCart = user.getShoppingCart();
        if (shoppingCart == null){
            user.setShoppingCart(new ShoppingCart());
        }
        List<CartItem> cartItems = user.getShoppingCart().getCartItems();

        List<CartItemDTO> cartItemsDTOs = new ArrayList<>();
        for (CartItem item: cartItems) {
            if (!item.getGame().isDeleted()){
                CartItemDTO map = modelMapper.map(item, CartItemDTO.class);
                cartItemsDTOs.add(map);
            }
        }
        return cartItemsDTOs;
    }

    @Override
    public UserEntity getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        return this.userRepository.findByUsername(currentUsername).get();
    }

    @Override
    public BigDecimal calculateTotalPrice() {
        BigDecimal sum = new BigDecimal(0);
        List<CartItemDTO> cartItems = getCartItems();
        for (CartItemDTO item: cartItems) {
            sum = sum.add(item.getTotal());
        }
        return sum;
    }


}
