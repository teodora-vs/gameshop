package com.softuni.gameshop.web;

import com.softuni.gameshop.model.DTO.ReviewDTO;
import com.softuni.gameshop.model.Review;
import com.softuni.gameshop.model.UserEntity;
import com.softuni.gameshop.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(ReviewRestController.class)
@AutoConfigureMockMvc
public class ReviewRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Test
    @WithMockUser
    public void testGetGameReviews() throws Exception {
        UserEntity author = new UserEntity().setId(1L).setFullName("Test");
        Long gameId = 1L;
        List<Review> mockReviews = Collections.singletonList(
                new Review().setId(1L)
                        .setAuthor(new UserEntity().setId(1L).setFullName("Test"))
                        .setStars(5)
                        .setTextContent("Great game!")
                        .setCreated(LocalDateTime.now())
        );

        when(reviewService.getAllReviewsForGame(gameId)).thenReturn(mockReviews);

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/api/{gameId}/reviews", gameId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value(author.getFullName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].stars").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].textContent").value("Great game!"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].created").isNotEmpty());
    }
}