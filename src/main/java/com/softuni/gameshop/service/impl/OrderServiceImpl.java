package com.softuni.gameshop.service.impl;

import com.softuni.gameshop.model.CartItem;
import com.softuni.gameshop.model.DTO.MyOrdersDTO;
import com.softuni.gameshop.model.DTO.OrderDTO;
import com.softuni.gameshop.model.DTO.OrderDetailsDTO;
import com.softuni.gameshop.model.DTO.OrderItemDTO;
import com.softuni.gameshop.model.Order;
import com.softuni.gameshop.model.ShoppingCart;
import com.softuni.gameshop.model.UserEntity;
import com.softuni.gameshop.repository.OrderRepository;
import com.softuni.gameshop.repository.ShoppingCartRepository;
import com.softuni.gameshop.repository.UserRepository;
import com.softuni.gameshop.service.OrderService;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private ModelMapper modelMapper;
    private final ShoppingCartRepository shoppingCartRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, ModelMapper modelMapper, ShoppingCartRepository shoppingCartRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    @Transactional
    public void addOrder(OrderDTO orderDTO) {
        UserEntity user = this.getCurrentUser();
        ShoppingCart shoppingCart = user.getShoppingCart();

        // Create a new order and set its properties
        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setUser(user);
        order.setAddress(orderDTO.getAddress());
        order.setTotalPrice(shoppingCart.getTotal());
        order.setCartItems(new ArrayList<>(shoppingCart.getCartItems())); // Clone the cart items

        // Clear the cart items from the original shopping cart
        shoppingCart.getCartItems().clear();

        // Save the changes
        shoppingCartRepository.save(shoppingCart);
        orderRepository.save(order);
    }

    public UserEntity getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        return this.userRepository.findByUsername(currentUsername).get();
    }

    @Override
    public List<MyOrdersDTO> getMyOrders() {
        Long id =  getCurrentUser().getId();
        List<MyOrdersDTO> myOrdersDTOs= new ArrayList<>();
        List<Order> byUserId = this.orderRepository.findByUserId(id);
        for (Order order: byUserId) {
            MyOrdersDTO map = modelMapper.map(order, MyOrdersDTO.class);
            myOrdersDTOs.add(map);
        }
        return myOrdersDTOs;
    }

    @Override
    public Order getOrderById(Long id) {
        return this.orderRepository.findById(id).get();
    }


    @Override
    public OrderDetailsDTO convertToOrderDetailsDTO(Order order) {
        List<CartItem> cartItems = order.getCartItems();
        List <OrderItemDTO> orderItemDTOS = new ArrayList<>();
        for (CartItem cartItem: cartItems) {
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            orderItemDTO.setGame(cartItem.getGame());
            orderItemDTO.setPrice(cartItem.getGame().getPrice());
            orderItemDTO.setQuantity(cartItem.getQuantity());
            orderItemDTO.setTotal(cartItem.getTotal());
            orderItemDTOS.add(orderItemDTO);
        }
        OrderDetailsDTO orderDetailsDTO = modelMapper.map(order, OrderDetailsDTO.class);
        orderDetailsDTO.setOrderItems(orderItemDTOS);
        return orderDetailsDTO;
    }

    @Override
    public OrderDetailsDTO getOrderDetailsById(Long orderId) {
        Order order = getOrderById(orderId);
        return convertToOrderDetailsDTO(order);
    }

}
