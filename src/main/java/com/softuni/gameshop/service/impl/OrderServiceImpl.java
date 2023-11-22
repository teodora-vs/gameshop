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
import com.softuni.gameshop.service.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, ShoppingCartRepository shoppingCartRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
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

    @Override
    public List<MyOrdersDTO> getMyOrders() {
        Long id =  this.getCurrentUser().getId();
        List<MyOrdersDTO> myOrdersDTOs= new ArrayList<>();
        List<Order> byUserId = this.orderRepository.findAllByUserIdOrderByOrderDateTimeDesc(id);
        for (Order order: byUserId) {
            MyOrdersDTO map = modelMapper.map(order, MyOrdersDTO.class);
            myOrdersDTOs.add(map);
        }
        return myOrdersDTOs;
    }

    @Override
    public Order getOrderById(Long id) {
        Optional<Order> order = this.orderRepository.findById(id);
        if (order.isEmpty()){
            throw new ObjectNotFoundException("Order with id " + id + " was not found");
        }
            return order.get();
    }

    @Override
    public OrderDetailsDTO getOrderDetailsById(Long orderId) {
        Order order = this.getOrderById(orderId);
        if (order.getUser().getUsername().equals(this.getCurrentUser().getUsername())){
            return convertToOrderDetailsDTO(order);
        }
        throw new ObjectNotFoundException("order with id " + order.getId() + " was not found");
    }

    @Override
    public OrderDetailsDTO convertToOrderDetailsDTO(Order order) {
        List<CartItem> cartItems = order.getCartItems();
        List <OrderItemDTO> orderItemDTOs = new ArrayList<>();
        for (CartItem cartItem: cartItems) {
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            orderItemDTO.setGame(cartItem.getGame());
            orderItemDTO.setQuantity(cartItem.getQuantity());
            orderItemDTO.setTotal(cartItem.getTotal());
            orderItemDTOs.add(orderItemDTO);
        }
        OrderDetailsDTO orderDetailsDTO = modelMapper.map(order, OrderDetailsDTO.class);
        orderDetailsDTO.setOrderItems(orderItemDTOs);
        return orderDetailsDTO;
    }

    @Override
    public List<AdminOrderDTO> getAllOrdersForAdmin(){
        List<Order> allOrders = this.orderRepository.findAllByOrderDateTimeDesc();
        List <AdminOrderDTO> adminOrderDTOs = new ArrayList<>();
        for (Order order: allOrders) {
            Optional<Order> byId = orderRepository.findById(order.getId());
            String receiver = byId.get().getUser().getFullName();

            AdminOrderDTO map = modelMapper.map(order, AdminOrderDTO.class);
            map.setReceiver(receiver);

            adminOrderDTOs.add(map);
        }

        return adminOrderDTOs;
    }

    @Override
    public AdminOrderDetailsDTO getOrderDetailsForAdmin(Long orderId) {
        Optional<Order> byId = this.orderRepository.findById(orderId);

        AdminOrderDetailsDTO map = modelMapper.map(byId.get(), AdminOrderDetailsDTO.class);
        map.setReceiver(byId.get().getUser().getFullName());
        return map;
    }

    public UserEntity getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        return this.userRepository.findByUsername(currentUsername).get();
    }

}
