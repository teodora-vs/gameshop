package com.softuni.gameshop.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testRegister() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/register")
                                .param("username", "pesho")
                                .param("email", "pesho@softuni.bg")
                                .param("fullName", "Pesho Petrov")
                                .param("password", "1234")
                                .param("confirmPassword", "1234")
                                .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));

    }

    @Test
    void testRegisterPasswordsDoNotMatch() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .param("password", "password")
                        .param("confirmPassword", "wrongpassword")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/register"))
                .andExpect(MockMvcResultMatchers.flash().attributeExists("passwordsDontMatch"));
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