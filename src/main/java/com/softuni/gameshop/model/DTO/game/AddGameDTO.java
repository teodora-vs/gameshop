package com.softuni.gameshop.model.DTO.game;

import com.softuni.gameshop.model.enums.GenreNamesEnum;
import com.softuni.gameshop.validation.YearNotInFuture;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class AddGameDTO {

    @NotBlank(message = "Title cannot be blank!")
    private String title;

    @NotBlank(message = "Description is required!")
    private String description;

    @YearNotInFuture(message = "The year should not be in the future!")
    @NotNull(message = "Year must be provided!")
    @Min(value = 1990, message = "Year must be greater than 1990")
    private Integer releaseYear;

    @NotNull(message = "Price is required!")
    @Positive(message = "Price must be a positive number!")
    private BigDecimal price;

    @NotBlank(message = "Image URL is required!")
    private String imageURL;

    @NotNull(message = "Genre is required!")
    private GenreNamesEnum genre;

    @Pattern(regexp = "^((?:https?:)?\\/\\/)?((?:www|m)\\.)?((?:youtube\\.com|youtu.be))(\\/(?:[\\w\\-]+\\?v=|embed\\/|v\\/)?)([\\w\\-]+)(\\S+)?$", message = "Invalid youtube url provided")
    private String videoURL;

    @NotNull(message = "quantity is required!")
    @Positive(message = "quantity must be a positive number!")
    private Integer quantity;

    public AddGameDTO() {
    }

    public Integer getQuantity() {
        return quantity;
    }

    public AddGameDTO setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public AddGameDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public GenreNamesEnum getGenre() {
        return genre;
    }

    public AddGameDTO setGenre(GenreNamesEnum genre) {
        this.genre = genre;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AddGameDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public AddGameDTO setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public AddGameDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public AddGameDTO setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public AddGameDTO setVideoURL(String videoURL) {
        this.videoURL = videoURL;
        return this;
    }
}
