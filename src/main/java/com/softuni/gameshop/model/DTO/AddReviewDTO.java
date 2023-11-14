package com.softuni.gameshop.model.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddReviewDTO {

    private Long gameId;

    @NotNull
    @Min(1)
    @Max(5)
    private int stars;

    @NotBlank(message = "You cannot post an empty review!")
    private String textContent;

    public AddReviewDTO() {
    }

    public int getStars() {
        return stars;
    }

    public AddReviewDTO setStars(int stars) {
        this.stars = stars;
        return this;
    }

    public String getTextContent() {
        return textContent;
    }

    public AddReviewDTO setTextContent(String textContent) {
        this.textContent = textContent;
        return this;
    }

    public Long getGameId() {
        return gameId;
    }

    public AddReviewDTO setGameId(Long gameId) {
        this.gameId = gameId;
        return this;
    }
}
