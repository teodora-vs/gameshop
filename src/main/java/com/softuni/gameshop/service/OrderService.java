package com.softuni.gameshop.service;

import com.softuni.gameshop.model.DTO.order.*;
import com.softuni.gameshop.model.Order;
import com.softuni.gameshop.model.UserEntity;

import java.util.List;

public interface OrderService {
    void addOrder(OrderDTO orderDTO);

    UserEntity getCurrentUser();

    OrderDetailsDTO convertToOrderDetailsDTO(Order order);
    OrderDetailsDTO getOrderDetailsById(Long orderId);

    List<AdminOrderDTO> getAllOrdersForAdmin();

    AdminOrderDetailsDTO getOrderDetailsForAdmin(Long orderId);

    List<MyOrdersDTO> getMyOrders();

    Order getOrderById(Long id);
}
