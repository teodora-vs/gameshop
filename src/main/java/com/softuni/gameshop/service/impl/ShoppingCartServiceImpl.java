package com.softuni.gameshop.service.impl;

import com.softuni.gameshop.model.*;
import com.softuni.gameshop.model.DTO.CartItemDTO;
import com.softuni.gameshop.model.enums.UserRoleEnum;
import com.softuni.gameshop.repository.GameRepository;
import com.softuni.gameshop.repository.ShoppingCartRepository;
import com.softuni.gameshop.repository.UserRepository;
import com.softuni.gameshop.repository.UserRoleRepository;
import com.softuni.gameshop.service.ShoppingCartService;
import com.softuni.gameshop.service.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ModelMapper modelMapper;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRoleRepository userRoleRepository;

    public ShoppingCartServiceImpl(GameRepository gameRepository, UserRepository userRepository, ShoppingCartRepository shoppingCartRepository, ModelMapper modelMapper, UserRoleRepository userRoleRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void removeFromCart(Long id) {
        UserEntity user = this.getCurrentUser();
        ShoppingCart shoppingCart = user.getShoppingCart();

        if (shoppingCart != null) {
            Optional<CartItem> optItem = shoppingCart.getCartItems().stream()
                    .filter(cartItem -> cartItem.getId().equals(id))
                    .findFirst();

            if (optItem.isPresent()) {
                CartItem itemToRemove = optItem.get();

                if (itemToRemove.getQuantity() > 1) {
                    itemToRemove.getGame().setQuantity(itemToRemove.getGame().getQuantity() + 1);
                    int currentQuantity = itemToRemove.getQuantity();
                    itemToRemove.setQuantity(currentQuantity - 1);
                    if (itemToRemove.getGame().getQuantity() > 0) {
                        itemToRemove.getGame().setDeleted(false);
                    }
                    shoppingCartRepository.save(shoppingCart);
                } else {
                    itemToRemove.getGame().setQuantity(itemToRemove.getGame().getQuantity() + 1);
                    if (itemToRemove.getGame().getQuantity() > 0) {
                        itemToRemove.getGame().setDeleted(false);
                        shoppingCart.getCartItems().remove(itemToRemove);

                        shoppingCartRepository.save(shoppingCart);
                    }
                }

            }
        }

    }

    @Override
    public void addToCart(Long gameId) {
        Game game = this.gameRepository.findById(gameId).orElseThrow(() -> new ObjectNotFoundException("Game with id: " + gameId + " not found!"));
        UserEntity user = this.getCurrentUser();
        ShoppingCart shoppingCart = user.getShoppingCart();
        UserRole userRole = this.userRoleRepository.findByRoleName(UserRoleEnum.USER);

        if (!user.getUserRoles().contains(userRole)) {
            return;
        }

        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
            shoppingCart.setUser(user);
            user.setShoppingCart(shoppingCart);
            shoppingCartRepository.save(shoppingCart);
        }

        if (shoppingCart.getCartItems() == null){
            shoppingCart.setCartItems(new ArrayList<>());
        }

        if (game.getQuantity() > 0 && !game.isDeleted()) {
            for (CartItem cartItem : shoppingCart.getCartItems()) {
                if (cartItem.getGame().getId().equals(game.getId())) {
                    cartItem.getGame().setQuantity(cartItem.getGame().getQuantity() - 1);
                    cartItem.setQuantity(cartItem.getQuantity() + 1);
                    shoppingCartRepository.save(shoppingCart);
                    return;
                }
            }

            CartItem cartItem = new CartItem().setGame(game).setQuantity(1);
            cartItem.getGame().setQuantity(cartItem.getGame().getQuantity() - 1);
            shoppingCart.getCartItems().add(cartItem);
            shoppingCartRepository.save(shoppingCart);
        }  else {
            throw new ObjectNotFoundException("Game with id :" + gameId + " is out of stock");
        }
    }


    @Override
    public List<CartItemDTO> getCartItems() {
        UserEntity user = this.getCurrentUser();
        ShoppingCart shoppingCart = user.getShoppingCart();
        if (shoppingCart == null ) {
            shoppingCart = new ShoppingCart();
            shoppingCart.setUser(user);
            user.setShoppingCart(shoppingCart);
            shoppingCartRepository.save(shoppingCart);
        }
        List<CartItem> cartItems = user.getShoppingCart().getCartItems();

        List<CartItemDTO> cartItemsDTOs = new ArrayList<>();
        for (CartItem item : cartItems) {
            if (!item.getGame().isDeleted()) {
                CartItemDTO map = modelMapper.map(item, CartItemDTO.class);
                cartItemsDTOs.add(map);
            }
        }

        return cartItemsDTOs;
    }

    @Override
    public BigDecimal getCartTotalPrice() {
        return this.getCurrentUser().getShoppingCart().getTotal();
    }

    @Override
    public void clearCart() {
        ShoppingCart shoppingCart = this.getCurrentUser().getShoppingCart();
        shoppingCart.getCartItems().clear();
        this.shoppingCartRepository.save(shoppingCart);
    }

    public UserEntity getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        return this.userRepository.findByUsername(currentUsername).get();
    }

}
