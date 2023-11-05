package com.softuni.gameshop.model.DTO;

import com.softuni.gameshop.model.enums.GenreNamesEnum;
import com.softuni.gameshop.validation.YearNotInFuture;
import jakarta.validation.constraints.*;

public class AddGameDTO {

    @NotBlank(message = "")
    @Size(min = 4, max = 32, message = "Size must be between 4 and 32 symbols!")
    private String title;

    @NotBlank(message = "Description is required!")
    private String description;

    @YearNotInFuture(message = "The year should not be in the future!")
    @NotNull(message = "Year must be provided!")
    @Min(value = 1990, message = "Year must be greater than 1990")
    private Integer releaseYear;

    @NotNull(message = "Price is required!")
    @Positive(message = "Price must be a positive number!")
    private Double price;

    @NotBlank(message = "Image URL is required!")
    private String imageURL;

    @NotNull(message = "Genre is required!")
    private GenreNamesEnum genre;

    @Pattern(regexp = "^((?:https?:)?\\/\\/)?((?:www|m)\\.)?((?:youtube\\.com|youtu.be))(\\/(?:[\\w\\-]+\\?v=|embed\\/|v\\/)?)([\\w\\-]+)(\\S+)?$", message = "Invalid youtube url provided")
    private String videoURL;

    public AddGameDTO() {
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

    public Double getPrice() {
        return price;
    }

    public AddGameDTO setPrice(Double price) {
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
