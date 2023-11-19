package com.softuni.gameshop.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class NavigationTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAnonymousUserNavigation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(content().string(containsString("Games")))
                .andExpect(content().string(containsString("Login")))
                .andExpect(content().string(containsString("Register")));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testUserNavigation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(content().string(containsString("Games")))
                .andExpect(content().string(containsString("Return Policy")))
                .andExpect(content().string(containsString("Shopping Cart")))
                .andExpect(content().string(containsString("My Orders")))
                .andExpect(content().string(containsString("Logout")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAdminNavigation() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(content().string(containsString("Games")))
                .andExpect(content().string(containsString("Return Policy")))
                .andExpect(content().string(containsString("All Orders")))
                .andExpect(content().string(containsString("Add Game")))
                .andExpect(content().string(containsString("Add Admin")))
                .andExpect(content().string(containsString("Logout")));
    }
}
