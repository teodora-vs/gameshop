package com.softuni.gameshop.web;
import com.softuni.gameshop.model.DTO.game.EditGameDTO;
import com.softuni.gameshop.model.DTO.game.GameDetailsDTO;
import com.softuni.gameshop.model.DTO.order.AdminOrderDetailsDTO;
import com.softuni.gameshop.model.UserEntity;
import com.softuni.gameshop.model.UserRole;
import com.softuni.gameshop.model.enums.GenreNamesEnum;
import com.softuni.gameshop.model.enums.UserRoleEnum;
import com.softuni.gameshop.repository.UserRepository;
import com.softuni.gameshop.service.GameService;
import com.softuni.gameshop.service.OrderService;
import com.softuni.gameshop.service.UserService;
import com.softuni.gameshop.service.impl.GameServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@AutoConfigureMockMvc
class AdminControllerTestIT {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private UserService userService;

    @Mock
    private GameServiceImpl gameService;

    @InjectMocks
    private AdminController adminController;


    @MockBean
    private UserRepository userRepository;

    @MockBean
    private OrderService orderService;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAddAdminPageAccessForAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-add"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAddAdminByUsername_Success() throws Exception {
        UserRole role = new UserRole().setRoleName(UserRoleEnum.USER);
        List<UserRole> currentRoles = new ArrayList<>();
        currentRoles.add(role);
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(new UserEntity().setUserRoles(currentRoles)));

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/admin/add")
                                .param("username", "testUser")
                .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/add"))
                .andExpect(MockMvcResultMatchers.flash().attribute("successfullyAdded", true));

        // Verifying that addAdminByUsername was called with the correct username
        verify(userService, times(1)).addAdminByUsername("testUser");
    }



    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAddGame() throws Exception {
        mockMvc.perform(
                        post("/games/add")
                                .param("title", "Test Game")
                                .param("description", "Test Description")
                                .param("price", "19.99")
                                .param("videoURL", "https://www.youtube.com/watch?v=example")
                                .param("imageURL", "https://example.com/thumbnail.jpg")
                                .param("releaseYear", "2023")
                                .param("genre", GenreNamesEnum.ADVENTURE.name())
                                .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/games"));

    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAddGameInvalidParams() throws Exception {
        mockMvc.perform(
                        post("/games/add")
                                .param("title", "")
                                .param("description", "Test Description")
                                .param("price", "19.99")
                                .param("videoURL", "https://www.youtube.com/watch?v=example")
                                .param("imageURL", "https://example.com/thumbnail.jpg")
                                .param("releaseYear", "2023")
                                .param("genre", GenreNamesEnum.ADVENTURE.name())
                                .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/games/add"))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.addGameDTO"));

        verify(gameService, never()).addGame(any());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteGame() throws Exception {
        mockMvc.perform(
                        post("/games/delete/1")
                                .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/games"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testEditGameValidationErrors() throws Exception {
        Long gameId = 1L;
        EditGameDTO editGameDTO = new EditGameDTO();

        mockMvc.perform(post("/games/edit/{id}", gameId)
                        .flashAttr("editGameDTO", editGameDTO)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/games/edit/{id}"))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.editGameDTO"));

        verify(gameService, never()).editGame(anyLong(), any(EditGameDTO.class));
    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testViewOrders() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/orders")
                ).andExpect(status().isOk())
                .andExpect(view().name("admin"))
                .andExpect(model().attributeExists("orders"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testViewOrderDetails() throws Exception {
        Long orderId = 1L;
        AdminOrderDetailsDTO adminOrderDetailsDTO = new AdminOrderDetailsDTO();

        when(orderService.getOrderDetailsForAdmin(orderId)).thenReturn(adminOrderDetailsDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/{id}", orderId))
                .andExpect(status().isOk())
                .andExpect(view().name("order-details-admin"))
                .andExpect(model().attributeExists("orderDetails"))
                .andExpect(model().attribute("orderDetails", adminOrderDetailsDTO));
    }
    



}