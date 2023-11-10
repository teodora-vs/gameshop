package com.softuni.gameshop.service.impl;

import com.softuni.gameshop.model.CartItem;
import com.softuni.gameshop.model.DTO.order.*;
import com.softuni.gameshop.model.Order;
import com.softuni.gameshop.model.ShoppingCart;
import com.softuni.gameshop.model.UserEntity;
import com.softuni.gameshop.repository.OrderRepository;
import com.softuni.gameshop.repository.ShoppingCartRepository;
import com.softuni.gameshop.repository.UserRepository;
import com.softuni.gameshop.service.OrderService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        Order order = new Order();
        order.setOrderDateTime(LocalDateTime.now());
        order.setUser(user);
        order.setPhoneNumber("+359" + orderDTO.getPhoneNumber());
        order.setAddress(orderDTO.getAddress());
        order.setTotalPrice(shoppingCart.getTotal());

        List<CartItem> orderedCartItems = new ArrayList<>();

        for (CartItem cartItem: shoppingCart.getCartItems()) {
            if (!cartItem.getGame().isDeleted()){
                orderedCartItems.add(cartItem);
            }
        }
        order.setCartItems(orderedCartItems);

        shoppingCart.getCartItems().clear();

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
    public OrderDetailsDTO getOrderDetailsById(Long orderId) {
        Order order = getOrderById(orderId);
        return convertToOrderDetailsDTO(order);
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


    public List<AdminOrderDTO> getAllOrdersForAdmin(){
        List<Order> all = this.orderRepository.findAllByOrderDateDesc();
        List <AdminOrderDTO> adminOrderDTOs = new ArrayList<>();
        for (Order order: all) {
            Optional<Order> byId = orderRepository.findById(order.getId());
            String receiver = byId.get().getUser().getFullName();

            AdminOrderDTO map = modelMapper.map(order, AdminOrderDTO.class);
            map.setReceiver(receiver);

            adminOrderDTOs.add(map);
        }

        return adminOrderDTOs;
    }

    public AdminOrderDetailsDTO getOrderDetailsForAdmin(Long orderId) {
        Optional<Order> byId = this.orderRepository.findById(orderId);

        List<CartItem> cartItems = byId.get().getCartItems();
        List <OrderItemDTO> orderItemDTOS = new ArrayList<>();
        for (CartItem cartItem: cartItems) {
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            orderItemDTO.setGame(cartItem.getGame());
            orderItemDTO.setPrice(cartItem.getGame().getPrice());
            orderItemDTO.setQuantity(cartItem.getQuantity());
            orderItemDTO.setTotal(cartItem.getTotal());
            orderItemDTOS.add(orderItemDTO);
        }
        Order order = getOrderById(orderId);

        AdminOrderDetailsDTO map = modelMapper.map(order, AdminOrderDetailsDTO.class);
        map.setReceiver(order.getUser().getFullName());
        return map;
    }

}
