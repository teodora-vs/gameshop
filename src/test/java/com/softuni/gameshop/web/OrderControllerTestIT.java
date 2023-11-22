package com.softuni.gameshop.web;
import com.softuni.gameshop.model.DTO.order.MyOrdersDTO;
import com.softuni.gameshop.model.DTO.order.OrderDTO;
import com.softuni.gameshop.model.DTO.order.OrderDetailsDTO;
import com.softuni.gameshop.model.Order;
import com.softuni.gameshop.model.ShoppingCart;
import com.softuni.gameshop.model.UserEntity;
import com.softuni.gameshop.service.OrderService;
import com.softuni.gameshop.service.ShoppingCartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingCartService shoppingCartService;

    @MockBean
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        Mockito.reset(orderService, shoppingCartService);
    }

    @Test
    @WithMockUser(username = "username")
    void testGetMyOrdersPage() throws Exception {
        List<MyOrdersDTO> orders = new ArrayList<>();

        when(orderService.getMyOrders()).thenReturn(orders);

        mockMvc.perform(MockMvcRequestBuilders.get("/my-orders"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.view().name("my-orders"))
                .andExpect(MockMvcResultMatchers.model().attribute("orders", orders));

        verify(orderService, times(1)).getMyOrders();
    }

    @Test
    @WithMockUser(username = "username")
    void testViewOrderDetails() throws Exception {
        Long orderId = 1L;
        OrderDetailsDTO orderDetails = new OrderDetailsDTO();

        when(orderService.getOrderDetailsById(orderId)).thenReturn(orderDetails);

        mockMvc.perform(MockMvcRequestBuilders.get("/my-orders/{id}", orderId))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.view().name("order-details"))
                .andExpect(MockMvcResultMatchers.model().attribute("orderDetails", orderDetails));

        verify(orderService, times(1)).getOrderDetailsById(orderId);
    }



}