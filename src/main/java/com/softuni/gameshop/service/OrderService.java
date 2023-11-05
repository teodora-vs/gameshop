package com.softuni.gameshop.service;

import com.softuni.gameshop.model.DTO.MyOrdersDTO;
import com.softuni.gameshop.model.DTO.OrderDTO;
import com.softuni.gameshop.model.DTO.OrderDetailsDTO;
import com.softuni.gameshop.model.Order;
import com.softuni.gameshop.model.UserEntity;

import java.util.List;

public interface OrderService {
    void addOrder(OrderDTO orderDTO);

    UserEntity getCurrentUser();

    OrderDetailsDTO convertToOrderDetailsDTO(Order order);
    OrderDetailsDTO getOrderDetailsById(Long orderId);

    List<MyOrdersDTO> getMyOrders();

    Order getOrderById(Long id);
}
