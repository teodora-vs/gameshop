package com.softuni.gameshop.web;

import com.softuni.gameshop.model.DTO.AddReviewDTO;
import com.softuni.gameshop.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class GameControllerTestIT {



    @Autowired
    private MockMvc mockMvc;


    @Mock
    private ReviewService reviewService;



    @Test
    @WithMockUser(username = "user")
    void testAddReviewEndpoint_InvalidStars() throws Exception {
        Long gameId = 1L;
        AddReviewDTO addReviewDTO = new AddReviewDTO();
      addReviewDTO.setTextContent("Test");

        mockMvc.perform(MockMvcRequestBuilders.post("/games/{gameId}/add-review", gameId)
                        .param("stars", "") // Add invalid stars value
                        .param("textContent", "Test")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/games/{gameId}"))
                .andExpect(flash().attribute("invalidStars", true));

    }

    @Test
    @WithMockUser(username = "user")
    void testAddReviewEndpoint_InvalidText() throws Exception {
        Long gameId = 1L;
        AddReviewDTO addReviewDTO = new AddReviewDTO();
        addReviewDTO.setTextContent("").setStars(4);

        mockMvc.perform(MockMvcRequestBuilders.post("/games/{gameId}/add-review", gameId)
                        .param("stars", "4") // Add invalid stars value
                        .param("textContent", "")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/games/{gameId}"))
                .andExpect(flash().attribute("invalidText", true));

        verify(reviewService, never()).createReview(any(AddReviewDTO.class));
    }
}