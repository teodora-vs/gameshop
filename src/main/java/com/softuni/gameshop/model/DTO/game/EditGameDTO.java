package com.softuni.gameshop.model.DTO.game;

import com.softuni.gameshop.model.enums.GenreNamesEnum;
import com.softuni.gameshop.validation.YearNotInFuture;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class EditGameDTO {

    private Long id;

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


    public EditGameDTO() {
    }

    public Integer getQuantity() {
        return quantity;
    }

    public EditGameDTO setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Long getId() {
        return id;
    }

    public EditGameDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public EditGameDTO setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EditGameDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public EditGameDTO setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public EditGameDTO setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getImageURL() {
        return imageURL;
    }

    public EditGameDTO setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public GenreNamesEnum getGenre() {
        return genre;
    }

    public EditGameDTO setGenre(GenreNamesEnum genre) {
        this.genre = genre;
        return this;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public EditGameDTO setVideoURL(String videoURL) {
        this.videoURL = videoURL;
        return this;
    }


}
