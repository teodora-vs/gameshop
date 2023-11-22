package com.softuni.gameshop.web;

import com.softuni.gameshop.model.DTO.UserRegisterDTO;
import com.softuni.gameshop.model.UserEntity;
import com.softuni.gameshop.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testRegister() throws Exception {
        when(userService.getByUsername("test")).thenReturn(Optional.empty());
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/register")
                                .param("username", "test")
                                .param("fullName", "test testov")
                                .param("email", "test@mail.com")
                                .param("password", "1234")
                                .param("confirmPassword", "1234")
                                .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("redirect:/login"));

    }

    @Test
    void testRegisterPasswordsDoNotMatch() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .param("password", "pass")
                        .param("confirmPassword", "wrongpass")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/register"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("passwordsDontMatch"));
    }

    @Test
    void testRegisterUsernameExists() throws Exception {
        when(userService.getByUsername("existingUsername")).thenReturn(Optional.of(new UserEntity().setUsername("existingUsername")));

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .param("username", "existingUsername")
                        .param("email", "test@softuni.bg")
                        .param("fullName", "Test User")
                        .param("password", "password")
                        .param("confirmPassword", "password")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/register"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("usernameExists"));

    }

    @Test
    void testRegisterWithInvalidData() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/register")
                                .param("username", "")
                                .param("email", "test@softuni.bg")
                                .param("fullName", "Test User")
                                .param("password", "password")
                                .param("confirmPassword", "password")
                                .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/register"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("org.springframework.validation.BindingResult.userRegisterDTO"))
                .andExpect(MockMvcResultMatchers.flash().attribute("org.springframework.validation.BindingResult.userRegisterDTO", Matchers.hasProperty("fieldErrors", Matchers.hasItem(Matchers.hasProperty("field", Matchers.equalTo("username"))))));
    }

    @Test
    void testLogin() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/login")
                                .param("username", "pesho")
                                .param("password", "1234")
                                .with(csrf())
                ).andExpect(status().is2xxSuccessful());
    }

}